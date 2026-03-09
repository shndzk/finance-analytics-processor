package com.skillbox.data.repository.impl;

import com.skillbox.data.model.Account;
import com.skillbox.data.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor // Генерирует конструктор для final полей
public class FileAccountRepository implements AccountRepository {

    private final String fileName; // ПОЛЕ ДОЛЖНО БЫТЬ FINAL

    @Override
    public List<Account> readAll() {
        // ... твоя логика чтения
        return java.util.Collections.emptyList();
    }
}
