package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.controller.option.MainMenuOption;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.service.TransactionService;

/**
 * Консольный контроллер для управления навигацией по главному меню.
 */
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
        // Состояние текущего сеанса работы пользователя
        TransactionFilterDto transactionFilter = new TransactionFilterDto();
        GroupOption groupOption = GroupOption.WITHOUT_GROUPING; // По умолчанию без группировки
        AggregateOption aggregateOption = AggregateOption.SUM;  // По умолчанию сумма
        Analytic analytics = null;

        while (true) {
            MainMenuOption selectedOption = selectMenu();

            switch (selectedOption) {
                case SEARCH_CRITERIA:
                    // Настраиваем фильтры (даты, суммы, категории)
                    transactionFilter = searchMenuController.getTransactionFilter();
                    break;

                case GROUP_OPTION:
                    // Выбираем поле группировки
                    groupOption = groupMenuController.getGroupOption();
                    break;

                case AGGREGATION_METHOD:
                    // Выбираем функцию (SUM, AVG, COUNT)
                    aggregateOption = aggregateMenuController.getAggregateOption();
                    break;

                case CALCULATE_ANALYTICS:
                    // Выполняем расчет через сервис
                    analytics = transactionService.calculateAnalytics(
                            transactionFilter,
                            groupOption,
                            aggregateOption
                    );
                    // Выводим результат в консоль (используется переопределенный toString в Analytic)
                    System.out.println(analytics);
                    break;

                case SAVE_ANALYTICS:
                    // Проверка: рассчитана ли уже аналитика
                    if (analytics == null) {
                        System.err.println("Ошибка: Необходимо сначала рассчитать аналитику (пункт 4)");
                        break;
                    }
                    // Сохраняем в JSON через репозиторий
                    saver.save(analytics);
                    break;

                case EXIT:
                    System.out.println("Завершение работы приложения. До свидания!");
                    return;
            }
        }
    }
}
