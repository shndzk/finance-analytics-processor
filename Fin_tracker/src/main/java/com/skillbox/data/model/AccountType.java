package com.skillbox.data.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AccountType {
    /**
     * Текущий счет обычно используется для повседневных финансовых операций, таких как оплата счетов, покупки и
     * переводы.
     */
    CHECKING(0),

    /**
     * Сберегательный счет предназначен для хранения сбережений и накоплений. Обычно он имеет ограниченное количество
     * транзакций в месяц, но предлагает процентный доход на сбережения.
     */
    SAVINGS(1),

    /**
     * Кредитный счет представляет собой счет, на котором отражаются операции, связанные с использованием кредита.
     * Кредитный лимит и процентные ставки играют ключевую роль для такого счета.
     */
    CREDIT(2);

    private final int type;

    AccountType(int type) {
        this.type = type;
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
}
