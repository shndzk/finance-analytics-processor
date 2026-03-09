package com.skillbox.service.impl;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.service.TransactionService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для обработки транзакций и расчета аналитики.
 */
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public Analytic calculateAnalytics(TransactionFilterDto filter,
                                       GroupOption group,
                                       AggregateOption aggregate) {

        // 1. Читаем все транзакции из репозитория
        List<Transaction> allTransactions = transactionRepository.readAll();

        // 2. Явно определяем функцию группировки (Classifier)
        // Если группа не выбрана — используем заглушку "Общий итог"
        Function<Transaction, String> classifier = (group != null && group != GroupOption.WITHOUT_GROUPING)
                ? group.getClassifier()
                : t -> "Общий итог";

        // 3. Явно определяем коллектор для агрегации
        // Если метод не выбран — по умолчанию считаем сумму (SUM)
        Collector<Transaction, ?, Double> collector = (aggregate != null)
                ? aggregate.getCollector()
                : AggregateOption.SUM.getCollector();

        // 4. Выполняем фильтрацию, группировку и расчет в одном Stream-потоке
        Map<String, Double> resultData = allTransactions.stream()
                .filter(filter.buildPredicate())
                .collect(Collectors.groupingBy(classifier, collector));

        // 5. Собираем и возвращаем объект Analytic
        return Analytic.builder()
                .date(LocalDateTime.now())
                .groupOption(group != null ? group.getName() : "Без группировки")
                .aggregateOption(aggregate != null ? aggregate.getName() : "Сумма")
                .filter(filter.toString()) // Убедитесь, что в TransactionFilterDto есть toString()
                .data(resultData)
                .build();
    }
}
