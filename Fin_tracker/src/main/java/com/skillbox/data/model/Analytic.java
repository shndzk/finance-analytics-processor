package com.skillbox.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Analytic {

    private LocalDateTime date;
    private String groupOption;
    private String aggregateOption;
    private String filter;
    private Map<String, Double> data;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===================================\n");

        sb.append("Дата: ").append(date.format(DATE_FORMATTER)).append("\n");

        sb.append("Имя: '").append(groupOption).append(" (").append(aggregateOption).append(")'\n");
        sb.append("Фильтр: ").append(filter).append("\n");
        sb.append("-----------------------------------\n");
        sb.append("Аналитика:\n");

        if (data != null) {
            data.forEach((key, value) -> {
                sb.append(key).append(": ");

                String lowerAggregate = aggregateOption.toLowerCase();

                if (lowerAggregate.contains("сумм")) {
                    long roundedSum = Math.round(value);
                    sb.append(roundedSum).append(" руб.\n");

                } else if (lowerAggregate.contains("средн")) {
                    sb.append(String.format("%.3f", value)).append(" руб.\n");

                } else if (lowerAggregate.contains("колич")) {
                    sb.append(value.intValue()).append("\n");

                } else {
                    sb.append(String.format("%.2f", value)).append(" руб.\n");
                }
            });
        }
        return sb.toString();
    }
}

