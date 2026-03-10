package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.SearchOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SearchMenuController extends AbstractMenuController<SearchOption> {

    private TransactionFilterDto filter = new TransactionFilterDto();

    public SearchMenuController() {
        super(SearchOption.class, "Выберите способ поиска транзакции");
    }

    public TransactionFilterDto getTransactionFilter() {
        while (true) {
            SearchOption option = selectMenu();
            switch (option) {
                case EXIT:
                    return filter;
                case ALL_TRANSACTION:
                    filter = new TransactionFilterDto();
                    System.out.println("Все фильтры сброшены.");
                    break;
                case SEARCH_BY_CATEGORY:
                    inputCategory();
                    break;
                case SEARCH_BY_DATES:
                    inputDates();
                    break;
                case SEARCH_BY_AMOUNT:
                    inputAmount();
                    break;
                case SEARCH_BY_COMMENT:
                    inputComment();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    private void inputComment() {
        System.out.print("Введите текст для поиска в комментариях (Enter — пропустить): ");
        String comment = scanner.next().trim();
        if (!comment.isEmpty()) {
            filter.setCommentToken(comment);
            System.out.println("Критерий поиска задан! Текст: '" + comment + "'");
        }
    }

    private void inputAmount() {
        try {
            System.out.print("Введите минимальную сумму (Enter — пропустить): ");
            String minStr = scanner.next().trim();
            if (!minStr.isEmpty()) {
                filter.setMinAmount(Double.parseDouble(minStr));
            }

            System.out.print("Введите максимальную сумму (Enter — пропустить): ");
            String maxStr = scanner.next().trim();
            if (!maxStr.isEmpty()) {
                filter.setMaxAmount(Double.parseDouble(maxStr));
            }
            System.out.println("Критерий поиска задан! Диапазон сумм сохранен.");
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введите числовое значение суммы.");
        }
    }

    private void inputDates() {
        try {
            System.out.print("Введите начальную дату (ГГГГ-ММ-ДД, Enter — пропустить): ");
            String startStr = scanner.next().trim();
            if (!startStr.isEmpty()) {
                filter.setStartDate(LocalDate.parse(startStr));
            }

            System.out.print("Введите конечную дату (ГГГГ-ММ-ДД, Enter — пропустить): ");
            String endStr = scanner.next().trim();
            if (!endStr.isEmpty()) {
                filter.setEndDate(LocalDate.parse(endStr));
            }
            System.out.println("Критерий поиска задан! Период сохранен.");
        } catch (DateTimeParseException e) {
            System.err.println("Ошибка: Неверный формат даты. Используйте ГГГГ-ММ-ДД.");
        }
    }

    private void inputCategory() {
        System.out.print("Введите категорию (Enter — поиск по всем): ");
        String category = scanner.next().trim();
        if (!category.isEmpty()) {
            filter.setCategory(category);
            System.out.println("Критерий поиска задан! Категория: '" + category + "'");
        }
    }
}
