package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.AccountType;

public class GroupMenuController extends AbstractMenuController<GroupOption> {

    private GroupOption selectedGroup = GroupOption.WITHOUT_GROUPING;

    public GroupMenuController() {
        super(GroupOption.class, "Выберите опцию группировки транзакции:");
    }

    // Принимаем объект фильтра из MainMenuController
    public GroupOption getGroupOption(TransactionFilterDto filter) {
        while (true) {
            GroupOption option = selectMenu();

            // Опция 0 — возврат в главное меню
            if (option == GroupOption.WITHOUT_GROUPING) {
                return selectedGroup;
            }

            // Опция 6 — Группировка по типу счета (с уточнением 0, 1, 2)
            if (option == GroupOption.BY_ACCOUNT_TYPE) {
                System.out.println("\nВыберите тип счёта для фильтрации:");
                System.out.println("0 — текущий");
                System.out.println("1 — сберегательный");
                System.out.println("2 — кредитный");
                System.out.print("Введите нужную цифру и нажмите Enter: ");

                try {
                    String input = scanner.next().trim();
                    int typeCode = Integer.parseInt(input);

                    // Получаем AccountType по коду (0, 1, 2)
                    AccountType selected = AccountType.of(typeCode);

                    if (selected != null) {
                        // КРИТИЧНО: Записываем выбор в ПЕРЕДАННЫЙ фильтр
                        filter.setTargetAccountType(selected);
                        System.out.println("Фильтр установлен: только " + selected.getDescription());
                    } else {
                        System.err.println("Ошибка: Тип счета с кодом " + typeCode + " не существует.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: Введите число (0, 1 или 2).");
                    continue;
                }
            } else {
                // Если выбрана любая другая группировка (не по счету),
                // сбрасываем фильтр по конкретному счету, чтобы видеть все данные
                filter.setTargetAccountType(null);
            }

            // Сохраняем выбранный способ группировки
            selectedGroup = option;
            System.out.println("Выбрана группировка: " + selectedGroup.getDescription());

            // После выбора опции возвращаемся в главное меню (согласно логике ТЗ)
            return selectedGroup;
        }
    }
}
