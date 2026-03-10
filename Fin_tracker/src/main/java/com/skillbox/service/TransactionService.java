package com.skillbox.service;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.Analytic;
import com.skillbox.exception.AppException;

public interface TransactionService {
    Analytic calculateAnalytics(TransactionFilterDto filter,
                                GroupOption group,
                                AggregateOption aggregate) throws AppException;
}
