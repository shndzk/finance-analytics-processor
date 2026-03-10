package com.skillbox.controller.dto;

import com.skillbox.data.model.Commentable;
import com.skillbox.data.model.Recurring;
import com.skillbox.data.model.Transaction;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

@Setter
public class TransactionFilterDto {

    private String category;
    private Double minAmount;
    private Double maxAmount;
    private String commentToken;
    private LocalDate startDate;
    private LocalDate endDate;


    private Predicate<Transaction> categoryPredicate() {
        return transaction -> {
            if (category == null || category.isBlank()) return true;
            return transaction.getCategory().equalsIgnoreCase(category);
        };
    }

    private Predicate<Transaction> amountPredicate() {
        return transaction -> {
            double amount = transaction.getFinalAmount();
            boolean minOk = minAmount == null || amount >= minAmount;
            boolean maxOk = maxAmount == null || amount <= maxAmount;
            return minOk && maxOk;
        };
    }

    private Predicate<Transaction> commentPredicate() {
        return transaction -> {
            if (commentToken == null || commentToken.isBlank()) return true;
            if (transaction instanceof Commentable c) {
                return c.getComments().stream()
                        .anyMatch(comm -> comm.toLowerCase().contains(commentToken.toLowerCase()));
            }
            return false;
        };
    }

    private Predicate<Transaction> datePredicate() {
        return transaction -> {
            LocalDateTime start = (startDate == null) ? LocalDateTime.MIN : startDate.atStartOfDay();
            LocalDateTime end = (endDate == null) ? LocalDateTime.MAX : endDate.atTime(23, 59, 59);

            boolean inRange = !transaction.getDateTime().isBefore(start) && !transaction.getDateTime().isAfter(end);

            boolean isRecurringInRange = (transaction instanceof Recurring r) && r.isExecutedBetween(start, end);

            return inRange || isRecurringInRange;
        };
    }

    public Predicate<Transaction> buildPredicate() {
        return categoryPredicate()
                .and(amountPredicate())
                .and(commentPredicate())
                .and(datePredicate());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(category != null && !category.isBlank() ? "Категория: '" + category + "'" : "Все категории");

        if (startDate != null || endDate != null) {
            sb.append(", Период: ");
            sb.append(startDate != null ? startDate : "начала времен");
            sb.append(" - ");
            sb.append(endDate != null ? endDate : "сегодня");
        } else {
            sb.append(", Период: весь доступный");
        }

        if (minAmount != null || maxAmount != null) {
            sb.append(", Сумма: ");
            if (minAmount != null) sb.append("от ").append(minAmount);
            if (maxAmount != null) sb.append(" до ").append(maxAmount);
        } else {
            sb.append(", Сумма: любая");
        }

        if (commentToken != null && !commentToken.isBlank()) {
            sb.append(", Комментарий содержит: '").append(commentToken).append("'");
        }

        return sb.toString();
    }
}
