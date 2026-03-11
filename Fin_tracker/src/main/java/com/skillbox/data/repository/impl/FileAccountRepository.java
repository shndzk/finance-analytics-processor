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
            List<Account> accounts = Files.lines(path)
                    .filter(line -> !line.isBlank())
                    .map(this::parseAccount)
                    .filter(java.util.Objects::nonNull)
                    .collect(Collectors.toList());

            this.cache = accounts.stream()
                    .collect(Collectors.toMap(Account::getAccountId, Function.identity(), (a1, a2) -> a1));

            return accounts;
        } catch (IOException e) {
            throw new DataAccessException("Ошибка при чтении файла аккаунтов: " + fileName, e);
        }
    }

    @Override
    public Account findById(int id) {
        if (cache == null) {
            try {
                readAll();
            } catch (DataAccessException e) {
                System.err.println("Критическая ошибка: не удалось загрузить аккаунты для поиска по ID: " + e.getMessage());
                return null;
            }
        }
        return cache != null ? cache.get(id) : null;
    }

    private Account parseAccount(String line) {
        try {
            String cleanLine = line.split("#")[0].trim();
            String[] parts = cleanLine.split(",");

            if (parts.length < 3) return null;

            int accountId = Integer.parseInt(parts[0].trim());
            int typeInt = Integer.parseInt(parts[1].trim());
            int userId = Integer.parseInt(parts[2].trim());

            AccountType type = AccountType.of(typeInt);

            return new AccountImpl(accountId, type, userId);
        } catch (Exception e) {
            System.err.println("Ошибка парсинга строки аккаунта: " + line);
            return null;
        }
    }
}