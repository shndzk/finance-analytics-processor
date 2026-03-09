package com.skillbox.data.model;

import java.math.BigDecimal;

/**
 * Интерфейс для операций с балансом счета.
 */
public interface BalanceOperations {

    /**
     * Метод для получения текущего баланса счета.
     *
     * @return Текущий баланс счета.
     */
    BigDecimal getBalance();

    /**
     * Метод для добавления транзакции в выписку.
     *
     * @param transaction Транзакция, которая будет добавлена.
     */
    void addTransaction(Transaction transaction);
}
