package com.skillbox.data.repository;

import com.skillbox.data.model.Account;
import java.util.List;

/**
 * Интерфейс для чтения аккаунтов (счетов) пользователей
 */
public interface AccountRepository {

    /**
     * Читает все записи со счетами
     * @return список счетов
     */
    List<Account> readAll();
}
