package com.skillbox.data.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.repository.AnalyticRepository;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация репозитория для сохранения аналитики в JSON файл.
 */
@RequiredArgsConstructor
public class FileAnalyticRepository implements AnalyticRepository {

    private final String fileName;

    // Настраиваем ObjectMapper для красивого вывода и работы с датами Java 8
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public void save(Analytic analytic) {
        try {
            File file = new File(fileName);
            List<Analytic> history = new ArrayList<>();

            // Если файл уже существует, сначала читаем старые записи, чтобы добавить новую (как в ТЗ)
            if (file.exists() && file.length() > 0) {
                history = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(List.class, Analytic.class));
            }

            // Добавляем новый расчет в список
            history.add(analytic);

            // Записываем обновленный список обратно в файл
            mapper.writeValue(file, history);

            System.out.println("Успешно сохранено в: " + fileName);

        } catch (IOException e) {
            System.err.println("Ошибка при записи JSON: " + e.getMessage());
        }
    }
}
