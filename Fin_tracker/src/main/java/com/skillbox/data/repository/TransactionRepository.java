package com.skillbox.data.repository;

import com.skillbox.data.model.Transaction;
import java.util.List;

/**
 * Интерфейс для чтения транзакций
 */
public interface TransactionRepository {

    /**
     * Читает все транзакции
     *
     * @return Список транзакций
     */
    List<Transaction> readAll();
}
