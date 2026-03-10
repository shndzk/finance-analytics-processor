package com.skillbox.controller.dto;

import com.skillbox.data.model.AccountType;
import com.skillbox.data.model.Commentable;
import com.skillbox.data.model.Recurring;
import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.AccountRepository;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import static javax.management.Query.and;

@Setter
public class TransactionFilterDto {

    private String category;
    private Double minAmount;
    private Double maxAmount;
    private String commentToken;
    private LocalDate startDate;
    private LocalDate endDate;
    private AccountType accountType;
    private AccountType targetAccountType;

    public Predicate<Transaction> accountTypePredicate(AccountRepository accountRepository) {
        return transaction -> {
            if (targetAccountType == null) return true;

            var account = accountRepository.findById(transaction.getAccountId());
            return account != null && account.getAccountType() == targetAccountType;
        };
    }


    private Predicate<Transaction> categoryPredicate() {
        return transaction -> {
            if (category == null || category.isBlank()) return true;
            return transaction.getCategory().equals(category);
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

    private Predicate<Transaction> filterByAccountType(AccountRepository repository) {
        return transaction -> {
            if (accountType == null) return true;
            var account = repository.findById(transaction.getAccountId());
            return account != null && account.getAccountType() == targetAccountType;
        };
    }

    public Predicate<Transaction> buildPredicate(AccountRepository repo) {
        return categoryPredicate()
                .and(amountPredicate())
                .and(commentPredicate())
                .and(datePredicate())
                .and(filterByAccountType(repo));
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(category != null && !category.isBlank() ? "Категория: '" + category + "'" : "Все категории");
        sb.append(", Тип счёта: ").append(accountType != null ? accountType : "любой");

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
