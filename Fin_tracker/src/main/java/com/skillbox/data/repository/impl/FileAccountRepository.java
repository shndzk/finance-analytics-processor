package com.skillbox.data.repository.impl;

import com.skillbox.data.model.Account;
import com.skillbox.data.model.AccountImpl;
import com.skillbox.data.model.AccountType;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.exception.DataAccessException;
import com.skillbox.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileAccountRepository implements AccountRepository {

    private final String fileName;

    private Map<Integer, Account> cache = null;

    @Override
    public List<Account> readAll() throws DataAccessException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new FileNotFoundException(fileName);
        }

        try {
            return Files.lines(path)
                    .filter(line -> !line.isBlank())
                    .map(this::parseAccount)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DataAccessException("Ошибка при чтении файла аккаунтов: " + fileName, e);
        }
    }

    @Override
    public Account findById(int id) {
        if (cache == null) {
            try {
                List<Account> allAccounts = readAll();
                cache = allAccounts.stream()
                        .collect(Collectors.toMap(Account::getAccountId, Function.identity()));
            } catch (DataAccessException e) {
                System.err.println("Ошибка инициализации кэша аккаунтов: " + e.getMessage());
                return null;
            }
        }
        return cache.get(id);
    }

    private Account parseAccount(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) return null;

            int accountId = Integer.parseInt(parts[0].trim());
            AccountType type = AccountType.of(Integer.parseInt(parts[1].trim()));
            int userId = Integer.parseInt(parts[2].trim());

            return new AccountImpl(accountId, type, userId);
        } catch (Exception e) {
            return null;
        }
    }
}
