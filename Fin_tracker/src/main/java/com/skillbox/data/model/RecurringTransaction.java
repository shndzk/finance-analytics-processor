package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecurringTransaction extends Transaction implements Recurring {

    private RecurrencePattern recurrencePattern; // hourly, daily, и т.д.
    private int occurrences; // количество повторений (например, 7)

    @Override
    public double getFinalAmount() {
        // По ТЗ: для повторяющихся берем исходную сумму из файла
        return getAmount();
    }

    @Override
    public boolean isExecutedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) return false;

        LocalDateTime currentOccurrence = getDateTime(); // Дата старта транзакции

        for (int i = 0; i < occurrences; i++) {
            // Если текущее повторение попало в диапазон
            if (!currentOccurrence.isBefore(startDate) && !currentOccurrence.isAfter(endDate)) {
                return true;
            }
            // Переходим к следующему повтору на основе Duration из Enum
            currentOccurrence = currentOccurrence.plus(recurrencePattern.getDuration());

            // Если мы уже «улетели» за конечную дату поиска — дальше искать нет смысла
            if (currentOccurrence.isAfter(endDate)) break;
        }
        return false;
    }

    @Override
    public LocalDateTime getNextOccurrence(LocalDateTime dateTime) {
        // Реализация по необходимости для доп. логики
        return getDateTime().plus(recurrencePattern.getDuration());
    }

    @Override
    public LocalDateTime getPreviousOccurrence(LocalDateTime dateTime) {
        return getDateTime().minus(recurrencePattern.getDuration());
    }

    @Override
    public BigDecimal getTransactionAmount(LocalDateTime dateTime) {
        return BigDecimal.valueOf(getAmount());
    }
}
