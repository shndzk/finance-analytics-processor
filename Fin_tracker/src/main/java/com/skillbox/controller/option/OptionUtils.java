package com.skillbox.controller.option;

import java.util.Arrays;

/**
 * Утилитный (вспомогательный) класс для работы с меню
 */
public final class OptionUtils {

    /**
     * Приватный конструктор, чтобы нельзя было создать экземпляр класса
     */
    private OptionUtils() {
    }

    /**
     * Возвращает элемент перечисления по значению поля option.
     *
     * @param enumClass класс перечисления.
     * @param option значение option
     * @param <E> тип перечисления, реализующего OptionEnum.
     * @return элемент перечисления, соответствующий значению option, или выбрасывает исключение, если элемент не
     * найден.
     */
    public static <E extends Enum<E> & MenuOption> E of(Class<E> enumClass, int option) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getOption() == option)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with option: " + option));
    }
}
