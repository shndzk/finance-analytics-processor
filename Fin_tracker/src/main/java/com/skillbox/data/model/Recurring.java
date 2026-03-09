package com.skillbox.data.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Интерфейс для транзакций, которые могут повторяться с определенной периодичностью.
 */
public interface Recurring {

    /**
     * Возвращает ближайшее дату / время следующего повторения транзакции
     *
     * @return дата следующего повторения транзакции.
     */
    LocalDateTime getNextOccurrence(LocalDateTime dateTime);

    /**
     * Возвращает ближайшее дату / время последнего выполнения транзакции
     *
     * @return дата следующего повторения транзакции.
     */
    LocalDateTime getPreviousOccurrence(LocalDateTime dateTime);

    /**
     * Возвращает сумму платежей по транзакции, которые были выполнены на дату / время
     *
     * @param dateTime дата для которой необходимо определить количество выполненных транзакций.
     * @return сумма платежей по выполненным транзакциям
     */
    BigDecimal getTransactionAmount(LocalDateTime dateTime);

    /**
     * Проверяет, выполнялась ли транзакция между двумя указанными датами/временами.
     *
     * @param startDate Начальная дата диапазона.
     * @param endDate   Конечная дата диапазона.
     * @return true, если транзакция выполнялась между startDate и endDate, иначе false.
     */
    boolean isExecutedBetween(LocalDateTime startDate, LocalDateTime endDate);
}
