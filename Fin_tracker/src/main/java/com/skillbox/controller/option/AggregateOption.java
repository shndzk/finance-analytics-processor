package com.skillbox.controller.option;

import com.skillbox.data.model.Transaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Перечисление для выбора функции агрегации.
 */
@Getter
@RequiredArgsConstructor
public enum AggregateOption implements MenuOption {
    SUM(0, "подсчёт суммы"),
    AVERAGE(1, "подсчёт среднего значения"),
    COUNT(2, "подсчёт количества");

    private final int option;
    private final String name;

    /**
     * Возвращает коллектор для Stream API на основе выбранной опции.
     */
    public Collector<Transaction, ?, Double> getCollector() {
        return switch (this) {
            case SUM -> Collectors.summingDouble(Transaction::getFinalAmount);
            case AVERAGE -> Collectors.averagingDouble(Transaction::getFinalAmount);
            case COUNT -> Collectors.collectingAndThen(Collectors.counting(), Long::doubleValue);
        };
    }

    @Override
    public String toStringRepresentation() {
        return option + " — " + name;
    }
}
