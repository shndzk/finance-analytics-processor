package com.skillbox.controller;

import com.skillbox.controller.option.GroupOption;

public class GroupMenuController extends AbstractMenuController<GroupOption> {

    public GroupMenuController() {
        super(GroupOption.class, "Выберите опцию группировки транзакции:");
    }

    public GroupOption getGroupOption() {
        return selectMenu();
    }
}
