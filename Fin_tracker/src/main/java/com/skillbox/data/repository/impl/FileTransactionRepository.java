package com.skillbox.data.repository.impl;

import com.skillbox.data.model.*;
import com.skillbox.data.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileTransactionRepository implements TransactionRepository {

    private final String fileName;

    @Override
    public List<Transaction> readAll() {
        try {
            // Читаем файл, пропускаем пустые строки и парсим каждую строку
            return Files.lines(Paths.get(fileName))
                    .filter(line -> !line.isBlank())
                    .map(this::parseTransaction)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла транзакций: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private Transaction parseTransaction(String line) {
        // Разделяем строку на 7 частей (6 общих полей + хвост со специфичными данными)
        String[] parts = line.split(",", 7);

        int accountId = Integer.parseInt(parts[0].trim());
        int id = Integer.parseInt(parts[1].trim());
        LocalDateTime dateTime = LocalDateTime.parse(parts[2].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String category = parts[3].trim();
        double amount = Double.parseDouble(parts[4].trim());
        String type = parts[5].trim();
        String extraData = parts.length > 6 ? parts[6].trim() : "";

        // Создаем конкретный объект в зависимости от типа
        return switch (type) {
            case "Taxable" -> {
                TaxableTransaction t = new TaxableTransaction();
                fillBaseFields(t, accountId, id, dateTime, category, amount);
                t.setTaxRate(Double.parseDouble(extraData));
                yield t;
            }
            case "ForeignCurrency" -> {
                ForeignCurrencyTransaction t = new ForeignCurrencyTransaction();
                fillBaseFields(t, accountId, id, dateTime, category, amount);
                t.setExchangeRate(Double.parseDouble(extraData));
                yield t;
            }
            case "Recurrent" -> {
                RecurringTransaction t = new RecurringTransaction();
                fillBaseFields(t, accountId, id, dateTime, category, amount);
                String[] recParts = extraData.split(";");
                t.setRecurrencePattern(RecurrencePattern.of(recParts[0].trim()));
                t.setOccurrences(Integer.parseInt(recParts[1].trim()));
                yield t;
            }
            case "Commentable" -> {
                CommentableTransaction t = new CommentableTransaction();
                fillBaseFields(t, accountId, id, dateTime, category, amount);
                t.setComments(Arrays.asList(extraData.split(";")));
                yield t;
            }
            default -> { // Regular
                RegularTransaction t = new RegularTransaction();
                fillBaseFields(t, accountId, id, dateTime, category, amount);
                yield t;
            }
        };
    }

    private void fillBaseFields(Transaction t, int accId, int id, LocalDateTime dt, String cat, double amt) {
        t.setAccountId(accId);
        t.setId(id);
        t.setDateTime(dt);
        t.setCategory(cat);
        t.setAmount(amt);
    }
}
