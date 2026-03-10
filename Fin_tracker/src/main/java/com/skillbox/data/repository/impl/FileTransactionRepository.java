package com.skillbox.data.repository.impl;

import com.skillbox.data.model.*;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.exception.DataAccessException;
import com.skillbox.exception.DataFormatException;
import com.skillbox.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileTransactionRepository implements TransactionRepository {

    private final String fileName;

    @Override
    public List<Transaction> readAll() throws DataAccessException {
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            throw new FileNotFoundException(fileName);
        }

        try {
            return Files.lines(path)
                    .filter(line -> !line.isBlank())
                    .map(line -> {
                        try {
                            return parseTransaction(line);
                        } catch (Exception e) {
                            throw new RuntimeException(new DataFormatException("Строка не соответствует формату: " + line, e));
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DataAccessException("Проблема при чтении файла транзакций: " + fileName, e);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof DataAccessException dae) throw dae;
            throw new DataAccessException("Системная ошибка при разборе транзакций", e);
        }
    }

    private Transaction parseTransaction(String line) {
        String[] parts = line.split(",", 7);

        int accountId = Integer.parseInt(parts[0].trim());
        int id = Integer.parseInt(parts[1].trim());
        LocalDateTime dateTime = LocalDateTime.parse(parts[2].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String category = parts[3].trim();
        double amount = Double.parseDouble(parts[4].trim());
        String type = parts[5].trim();
        String extraData = parts.length > 6 ? parts[6].trim() : "";

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
            default -> {
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
