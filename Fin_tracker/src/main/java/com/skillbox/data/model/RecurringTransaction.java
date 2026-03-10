package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecurringTransaction extends Transaction implements Recurring {

    private RecurrencePattern recurrencePattern;
    private int occurrences;

    @Override
    public double getFinalAmount() {
        return getAmount();
    }

    @Override
    public boolean isExecutedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) return false;

        LocalDateTime currentOccurrence = getDateTime();

        for (int i = 0; i < occurrences; i++) {
            if (!currentOccurrence.isBefore(startDate) && !currentOccurrence.isAfter(endDate)) {
                return true;
            }
            currentOccurrence = currentOccurrence.plus(recurrencePattern.getDuration());

            if (currentOccurrence.isAfter(endDate)) break;
        }
        return false;
    }

    @Override
    public LocalDateTime getNextOccurrence(LocalDateTime dateTime) {
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
