package com.skillbox.controller.option;

import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.AccountRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum GroupOption implements MenuOption {
    WITHOUT_GROUPING(0, "без группировки", (t, repo) -> "Общий итог"),

    BY_MONTH(1, "группировать по месяцам", (t, repo) ->
            t.getDateTime().getMonth().getDisplayName(TextStyle.FULL, new Locale("ru"))),

    BY_YEAR(2, "группировать по годам", (t, repo) ->
            String.valueOf(t.getDateTime().getYear())),

    BY_DAY_OF_WEEK(3, "группировать по дню недели", (t, repo) ->
            t.getDateTime().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru"))),

    BY_CATEGORY(4, "группировать по категории", (t, repo) ->
            t.getCategory()),

    BY_INCOME_EXPENSE(5, "считать доходы и расходы", (t, repo) ->
            t.getFinalAmount() >= 0 ? "Доход" : "Расход"),

    BY_ACCOUNT_TYPE(6, "группировка по типу счёта", (t, repo) -> {
        var account = repo.findById(t.getAccountId());
        if (account == null) return "Счёт не найден (ID: " + t.getAccountId() + ")";
        return account.getAccountType().toString();
    }),

    BY_USER_ID(7, "группировка по ID пользователя", (t, repo) -> {
        var account = repo.findById(t.getAccountId());

        return account != null
                ? "Пользователь ID: " + account.getUserId()
                : "Неизвестный пользователь";
    });

    private final int option;
    private final String description;
    private final GroupClassifier classifier;

    @Override
    public String getName() {
        return description;
    }

    @Override
    public String toStringRepresentation() {
        return option + " — " + description;
    }

    public Function<Transaction, String> getClassifier(AccountRepository repository) {
        return transaction -> classifier.apply(transaction, repository);
    }

    @FunctionalInterface
    public interface GroupClassifier {
        String apply(Transaction t, AccountRepository repo);
    }
}