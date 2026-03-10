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
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public Analytic calculateAnalytics(TransactionFilterDto filter,
                                       GroupOption group,
                                       AggregateOption aggregate) throws AppException {

        // 1. Загружаем все транзакции из репозитория
        List<Transaction> transactions = transactionRepository.readAll();

        // 2. Создаем финальный фильтр.
        // Передаем accountRepository, чтобы фильтр мог сопоставить ID счета с его типом (0, 1, 2)
        Predicate<Transaction> finalFilter = filter.buildPredicate(accountRepository);

        // 3. Определяем функцию группировки (классификатор)
        Function<Transaction, String> classifier = (group != null && group != GroupOption.WITHOUT_GROUPING)
                ? group.getClassifier(accountRepository)
                : t -> "Общий итог";

        // 4. Определяем способ агрегации (сумма, среднее или количество)
        // Выносим в переменную, чтобы избежать ошибки компиляции (wildcard capture)
        Collector<Transaction, ?, Double> collector = (aggregate != null)
                ? aggregate.getCollector()
                : AggregateOption.SUM.getCollector();

        // 5. Выполняем анализ через Stream API
        Map<String, Double> resultData = transactions.stream()
                .filter(finalFilter)
                .collect(Collectors.groupingBy(classifier, collector));

        // 6. Формируем и возвращаем объект аналитики для вывода или сохранения
        return Analytic.builder()
                .date(LocalDateTime.now())
                .groupOption(group != null ? group.getDescription() : "Без группировки")
                .aggregateOption(aggregate != null ? aggregate.getName() : "Сумма")
                .filter(filter.toString()) // Здесь будет выведен ваш настроенный фильтр
                .data(resultData)
                .build();
    }
}

