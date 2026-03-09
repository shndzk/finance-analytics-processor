package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.SearchOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Консольный контроллер для управления навигацией по функционалу поиска транзакций.
 */
public class SearchMenuController extends AbstractMenuController<SearchOption> {

    public SearchMenuController() {
        super(SearchOption.class, "Выберите способ поиска транзакции");
    }

    public TransactionFilterDto getTransactionFilter() {
        TransactionFilterDto filter = new TransactionFilterDto();
        while (true) {
            SearchOption option = selectMenu();
            switch (option) {
                case EXIT:
                    // Возвращаем накопленный фильтр в главное меню
                    return filter;
                case ALL_TRANSACTION:
                    // Сбрасываем фильтр до пустого
                    filter = new TransactionFilterDto();
                    System.out.println("Все фильтры сброшены.");
                    break;
                case SEARCH_BY_CATEGORY:
                    filter = inputCategory(filter);
                    break;
                case SEARCH_BY_DATES:
                    filter = inputDates(filter);
                    break;
                case SEARCH_BY_AMOUNT:
                    filter = inputAmount(filter);
                    break;
                case SEARCH_BY_COMMENT:
                    filter = inputComment(filter);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    private TransactionFilterDto inputComment(TransactionFilterDto filter) {
        System.out.print("Введите комментарий или его часть (Enter — для поиска всех): ");
        String comment = scanner.next().trim();
        if (!comment.isEmpty()) {
            filter.setCommentToken(comment);
        }
        return filter;
    }

    private TransactionFilterDto inputAmount(TransactionFilterDto filter) {
        try {
            System.out.print("Введите минимальную сумму (Enter — не ограничивать): ");
            String minStr = scanner.next().trim();
            if (!minStr.isEmpty()) {
                filter.setMinAmount(Double.parseDouble(minStr));
            }

            System.out.print("Введите максимальную сумму (Enter — не ограничивать): ");
            String maxStr = scanner.next().trim();
            if (!maxStr.isEmpty()) {
                filter.setMaxAmount(Double.parseDouble(maxStr));
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введите числовое значение суммы.");
        }
        return filter;
    }

    private TransactionFilterDto inputDates(TransactionFilterDto filter) {
        try {
            System.out.print("Введите начальную дату (ГГГГ-ММ-ДД, Enter — не ограничивать): ");
            String startStr = scanner.next().trim();
            if (!startStr.isEmpty()) {
                filter.setStartDate(LocalDate.parse(startStr));
            }

            System.out.print("Введите конечную дату (ГГГГ-ММ-ДД, Enter — не ограничивать): ");
            String endStr = scanner.next().trim();
            if (!endStr.isEmpty()) {
                filter.setEndDate(LocalDate.parse(endStr));
            }
        } catch (DateTimeParseException e) {
            System.err.println("Ошибка: Неверный формат даты. Используйте ГГГГ-ММ-ДД.");
        }
        return filter;
    }

    private TransactionFilterDto inputCategory(TransactionFilterDto filter) {
        System.out.print("Введите категорию (Enter — поиск по всем категориям): ");
        String category = scanner.next().trim();
        if (!category.isEmpty()) {
            filter.setCategory(category);
        }
        return filter;
    }
}
