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
            LocalDateTime transDate = transaction.getDateTime();
            LocalDateTime start = startDate == null ? LocalDateTime.MIN : startDate.atStartOfDay();
            LocalDateTime end = endDate == null ? LocalDateTime.MAX : endDate.atTime(23, 59, 59);

            boolean inRange = !transDate.isBefore(start) && !transDate.isAfter(end);
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

        // Красивое описание категории
        sb.append(category != null && !category.isBlank() ? "Категория: " + category : "Все категории");

        // Красивое описание дат
        if (startDate != null || endDate != null) {
            sb.append(", Даты: ");
            sb.append(startDate != null ? "с " + startDate : "с начала времен");
            sb.append(endDate != null ? " по " + endDate : " по сегодня");
        } else {
            sb.append(", Период: весь доступный");
        }

        // Красивое описание сумм
        if (minAmount != null || maxAmount != null) {
            sb.append(", Сумма: ");
            sb.append(minAmount != null ? "от " + minAmount : "-∞");
            sb.append(maxAmount != null ? " до " + maxAmount : " до +∞");
        } else {
            sb.append(", Сумма: любая");
        }

        // Красивое описание комментария
        if (commentToken != null && !commentToken.isBlank()) {
            sb.append(", Комментарий содержит: '").append(commentToken).append("'");
        }

        return sb.toString();
    }
}
