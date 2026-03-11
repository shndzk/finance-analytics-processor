package com.skillbox.data.repository;

import com.skillbox.data.model.Analytic;
import com.skillbox.exception.AppException;

public interface AnalyticRepository {
    void save(Analytic analytic) throws AppException;
}
