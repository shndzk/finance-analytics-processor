package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.controller.option.MainMenuOption;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.exception.AppException;
import com.skillbox.service.TransactionService;

public class MainMenuController extends AbstractMenuController<MainMenuOption> {

    private final TransactionService transactionService;
    private final AnalyticRepository saver;
    private final SearchMenuController searchMenuController;
    private final GroupMenuController groupMenuController;
    private final AggregateMenuController aggregateMenuController;

    public MainMenuController(TransactionService transactionService, AnalyticRepository saver) {
        super(MainMenuOption.class, "Анализ финансов");
        this.transactionService = transactionService;
        this.saver = saver;
        this.searchMenuController = new SearchMenuController();
        this.groupMenuController = new GroupMenuController();
        this.aggregateMenuController = new AggregateMenuController();
    }

    public void start() {
        goMainMenu();
    }

    private void goMainMenu() {
        TransactionFilterDto transactionFilter = new TransactionFilterDto();

        GroupOption groupOption = GroupOption.WITHOUT_GROUPING;
        AggregateOption aggregateOption = AggregateOption.SUM;
        Analytic analytics = null;

        while (true) {
            MainMenuOption selectedOption = selectMenu();

            switch (selectedOption) {
                case SEARCH_CRITERIA:
                    transactionFilter = searchMenuController.getTransactionFilter();
                    break;

                case GROUP_OPTION:
                    groupOption = groupMenuController.getGroupOption(transactionFilter);
                    break;

                case AGGREGATION_METHOD:
                    aggregateOption = aggregateMenuController.getAggregateOption();
                    break;

                case CALCULATE_ANALYTICS:
                    try {
                        analytics = transactionService.calculateAnalytics(
                                transactionFilter,
                                groupOption,
                                aggregateOption
                        );
                        System.out.println(analytics);
                    } catch (AppException e) {
                        System.err.println("Ошибка аналитики: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Критический сбой: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case SAVE_ANALYTICS:
                    if (analytics == null) {
                        System.err.println("Ошибка: Сначала рассчитайте аналитику (пункт 4)");
                        break;
                    }
                    try {
                        saver.save(analytics);
                        System.out.println("Аналитика успешно сохранена в файл.");
                    } catch (AppException e) {
                        System.err.println("Ошибка при сохранении: " + e.getMessage());
                    }
                    break;

                case EXIT:
                    System.out.println("Завершение работы. До свидания!");
                    return;
            }
        }
    }
}
