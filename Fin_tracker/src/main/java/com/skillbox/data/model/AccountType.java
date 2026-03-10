package com.skillbox.data.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AccountType {
    CHECKING(0, "Текущий счёт"),
    SAVINGS(1, "Сберегательный счёт"),
    CREDIT(2, "Кредитный счёт");

    private final int type;
    private final String description;

    AccountType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    private static final Map<Integer, AccountType> MAP = Arrays
            .stream(AccountType.values())
            .collect(Collectors.toMap(AccountType::getType, Function.identity()));

    public static AccountType of(int type) {
        return MAP.get(type);
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return description;
    }
}
