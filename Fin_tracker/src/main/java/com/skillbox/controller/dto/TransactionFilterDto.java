package com.skillbox.controller.dto;

import com.skillbox.data.model.Recurring;
import com.skillbox.data.model.Transaction;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Класс для хранения фильтра по транзакциям.
 */
public class TransactionFilterDto {

    LocalDate startDate;
    LocalDate endDate;

    /**
     * Создает предикат для фильтрации транзакций по диапазону дат. Также вернет те Recurring транзакции, которые будут
     * или были выполнены в указанный диапазон дат
     *
     * @return Предикат для фильтрации транзакций по диапазону дат.
     */
    private Predicate<Transaction> datePredicate() {
        return transaction -> {
            LocalDateTime date = null; // TODO: здесь необходимо получить дату транзакции
            LocalDateTime start = startDate == null ? null : startDate.atStartOfDay();
            LocalDateTime end = endDate == null ? null : endDate.atStartOfDay();
            return (start == null || !date.isBefore(start)) &&
                    (end == null || !date.isAfter(end))
                    || (transaction instanceof Recurring && ((Recurring) transaction).isExecutedBetween(start, end));
        };
    }

    /**
     * Создает предикат для фильтрации транзакций по комментарию или его части. Фильтруются только транзакции,
     * имплементирующие интерфейс Commentable. Если токен пустой или null, то возвращается предикат, который всегда
     * вернет true
     *
     * @return Предикат для фильтрации транзакций по комментарию.
     */
    private Predicate<Transaction> commentPredicate() {
        // TODO: реализуйте метод, возвращающий предикат для фильтрации транзакций по комментарию
        return null;
    }

    /**
     * Создает предикат для фильтрации транзакций по диапазону суммы.
     *
     * @return Предикат для фильтрации транзакций по диапазону суммы.
     */
    private Predicate<Transaction> amountPredicate() {
        // TODO: реализуйте метод, возвращающий предикат для фильтрации транзакций по диапазону суммы
        return null;
    }

    /**
     * Создает предикат для фильтрации транзакций по категории.
     *
     * @return Предикат для фильтрации транзакций по категории.
     */
    private Predicate<Transaction> categoryPredicate() {
        // TODO: реализуйте метод, возвращающий предикат для фильтрации транзакций по категории
        return null;
    }

    /**
     * Собирает предикат для фильтрации транзакции.
     *
     * @return Предикат для фильтрации транзакции.
     */
    public Predicate<Transaction> buildPredicate() {
        return categoryPredicate()
                .and(amountPredicate())
                .and(commentPredicate())
                .and(datePredicate())
                .and(amountPredicate());
    }
}
