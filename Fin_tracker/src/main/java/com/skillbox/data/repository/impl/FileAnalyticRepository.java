package com.skillbox.data.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.exception.DataAccessException;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FileAnalyticRepository implements AnalyticRepository {

    private final String fileName;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public void save(Analytic analytic) throws DataAccessException {
        try {
            File file = new File(fileName);
            List<Analytic> history = new ArrayList<>();

            if (file.exists() && file.length() > 0) {
                history = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(List.class, Analytic.class));
            }

            history.add(analytic);

            mapper.writeValue(file, history);

        } catch (IOException e) {
            throw new DataAccessException("Не удалось сохранить аналитику в файл: " + fileName, e);
        }
    }
}
