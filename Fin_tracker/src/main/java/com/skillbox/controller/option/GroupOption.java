package com.skillbox.controller.option;

import com.skillbox.data.model.Transaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum GroupOption implements MenuOption {
    WITHOUT_GROUPING(0, "вернуться назад (без группировки)"),
    BY_MONTH(1, "группировать по месяцам"),
    BY_YEAR(2, "группировать по годам"),
    BY_DAY_OF_WEEK(3, "группировать по дню недели"),
    BY_CATEGORY(4, "группировать по категории"),
    BY_INCOME_EXPENSE(5, "считать доходы и расходы"),
    BY_ACCOUNT_TYPE(6, "группировать по типу счёта"),
    BY_USER_ID(7, "группировать по ID пользователя");

    private final int option;
    private final String name;

    public Function<Transaction, String> getClassifier() {
        return switch (this) {
            case BY_MONTH -> t -> t.getDateTime().getMonth().toString();
            case BY_YEAR -> t -> String.valueOf(t.getDateTime().getYear());
            case BY_DAY_OF_WEEK -> t -> t.getDateTime().getDayOfWeek().toString();
            case BY_CATEGORY -> Transaction::getCategory;
            case BY_INCOME_EXPENSE -> t -> t.getFinalAmount() >= 0 ? "Доход" : "Расход";
            // Для типа счета и ID пользователя в идеале нужна связь с Account,
            // но для начала можно группировать по AccountId
            case BY_ACCOUNT_TYPE, BY_USER_ID, WITHOUT_GROUPING -> t -> "Общий итог";
            default -> t -> "Без группировки";
        };
    }

    @Override
    public String toStringRepresentation() {
        return option + " — " + name;
    }
}
