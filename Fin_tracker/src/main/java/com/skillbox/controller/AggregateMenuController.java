package com.skillbox.controller;

import com.skillbox.controller.option.AggregateOption;

public class AggregateMenuController extends AbstractMenuController<AggregateOption> {

    public AggregateMenuController() {
        super(AggregateOption.class, "Выберите способ агрегации транзакций:");
    }

    /**
     * Метод для запуска меню выбора агрегации.
     * @return выбранная пользователем AggregateOption
     */
    public AggregateOption getAggregateOption() {
        return selectMenu();
    }
}
