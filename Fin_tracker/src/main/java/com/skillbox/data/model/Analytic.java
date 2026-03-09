package com.skillbox.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, хранящий результаты расчета аналитики транзакций.
 */
@Data
@Builder // Добавь эту аннотацию!
@AllArgsConstructor // Нужна для работы @Builder
@NoArgsConstructor  // Нужна для Jackson (JSON)
public class Analytic {

    private LocalDateTime date;
    private String groupOption;
    private String aggregateOption;
    private String filter;
    private Map<String, Double> data;

    @Override
    public String toString() {
        // Форматированный вывод в консоль согласно ТЗ
        StringBuilder sb = new StringBuilder();
        sb.append("===================================\n");
        sb.append("Дата: ").append(date).append("\n");
        sb.append("Имя: '").append(groupOption).append(" (").append(aggregateOption).append(")'\n");
        sb.append("Фильтр: ").append(filter).append("\n");
        sb.append("-----------------------------------\n");
        sb.append("Аналитика:\n");
        if (data != null) {
            data.forEach((key, value) ->
                    sb.append(key).append(": ").append(String.format("%.2f", value)).append("\n")
            );
        }
        return sb.toString();
    }
}
