package com.skillbox.controller;

import com.skillbox.controller.option.GroupOption;

public class GroupMenuController extends AbstractMenuController<GroupOption> {

    public GroupMenuController() {
        super(GroupOption.class, "Выберите опцию группировки транзакции:");
    }

    /**
     * Метод для запуска меню выбора группировки.
     * @return выбранная пользователем GroupOption
     */
    public GroupOption getGroupOption() {
        return selectMenu();
    }
}
