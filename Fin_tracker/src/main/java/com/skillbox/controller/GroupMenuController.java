package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.AccountType;

public class GroupMenuController extends AbstractMenuController<GroupOption> {

    private GroupOption selectedGroup = GroupOption.WITHOUT_GROUPING;

    private AccountType selectedAccountType;

    public GroupMenuController() {
        super(GroupOption.class, "Выберите опцию группировки транзакции:");
    }

    public void setAccountTypeFilter(TransactionFilterDto filter) {
        System.out.println("Выберите тип счёта для фильтрации:");
        System.out.println("0 — текущий, 1 — сберегательный, 2 — кредитный счёт");

        int typeCode = Integer.parseInt(scanner.next().trim());
        filter.setTargetAccountType(AccountType.of(typeCode));

        System.out.println("Фильтр по типу счёта установлен: " + AccountType.of(typeCode));
    }


    public GroupOption getGroupOption(TransactionFilterDto filter) {
        while (true) {
            GroupOption option = selectMenu();

            if (option == GroupOption.WITHOUT_GROUPING) {
                return selectedGroup;
            }

            if (option == GroupOption.BY_ACCOUNT_TYPE) {
                System.out.println("Выберите тип счёта для группировки:");
                System.out.println("0 — текущий");
                System.out.println("1 — сберегательный");
                System.out.println("2 — кредитный");
                System.out.print("Введите нужную опцию: ");

                String input = scanner.next().trim();
                try {
                    int typeCode = Integer.parseInt(input);
                    this.selectedAccountType = AccountType.of(typeCode);
                    System.out.println("Выбран тип счёта: " + selectedAccountType.getDescription());
                } catch (Exception e) {
                    System.err.println("Ошибка: неверный тип счёта. Попробуйте снова.");
                    continue;
                }
            }

            selectedGroup = option;
            System.out.println("Опция группировки установлена: " + selectedGroup.getDescription());
            return selectedGroup; // Или уберите return, если хотите оставить пользователя в цикле
        }
    }
}
