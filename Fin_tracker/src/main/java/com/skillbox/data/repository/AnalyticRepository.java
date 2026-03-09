package com.skillbox.data.repository;

import com.skillbox.data.model.Analytic;

/**
 * Интерфейс для сохранения аналитики
 */
public interface AnalyticRepository {

    /**
     * Сохраняет аналитику, добавляя ее к уже существующим записям.
     *
     * @param analytic данные аналитики.
     */
    void save(Analytic analytic);
}
