package com.skillbox.service.impl;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.exception.AppException;
import com.skillbox.service.TransactionService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public Analytic calculateAnalytics(TransactionFilterDto filter,
                                       GroupOption group,
                                       AggregateOption aggregate) throws AppException {

        List<Transaction> transactions = transactionRepository.readAll();

        Function<Transaction, String> classifier = (group != null && group != GroupOption.WITHOUT_GROUPING)
                ? group.getClassifier(accountRepository)
                : t -> "Общий итог";

        var collector = (aggregate != null)
                ? aggregate.getCollector()
                : AggregateOption.SUM.getCollector();

        Map<String, Double> resultData = transactions.stream()
                .filter(filter.buildPredicate())
                .collect(Collectors.groupingBy(classifier, collector));

        return Analytic.builder()
                .date(LocalDateTime.now())
                .groupOption(group != null ? group.getDescription() : "Без группировки")
                .aggregateOption(aggregate != null ? aggregate.getName() : "Сумма")
                .filter(filter.toString())
                .data(resultData)
                .build();
    }
}
