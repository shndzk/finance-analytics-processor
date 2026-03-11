package com.skillbox.controller;

import com.skillbox.controller.option.AggregateOption;

public class AggregateMenuController extends AbstractMenuController<AggregateOption> {

    public AggregateMenuController() {
        super(AggregateOption.class, "Выберите способ агрегации транзакций:");
    }

    public AggregateOption getAggregateOption() {
        return selectMenu();
    }
}
