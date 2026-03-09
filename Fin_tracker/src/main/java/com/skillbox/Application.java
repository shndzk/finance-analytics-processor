package com.skillbox;


import com.skillbox.controller.MainMenuController;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.service.TransactionService;

public class Application {

    public static void main(String[] args) {
        // проверка аргументов командной строки
        if (args.length < 3) {
            // TODO: создайте собственное исключение для обработки этой бизнес-ошибки
            throw new IllegalArgumentException(
                    "Необходимо указать имена файлов для входных данных аккаунтов и транзакций, а также для выходного файла.");
        }
        // имя входного файла с информацией об аккаунтах
        String accountFilename = args[0];
        // имя входного файла с информацией о транзакциях
        String transactionFilename = args[1];
        // имя выходного файла для записи результата
        String outputFilename = args[2];

        // TODO: исправьте инициализацию сервисов
        AccountRepository accountReader = null;
        TransactionRepository transactionReader = null;
        TransactionService transactionService = null;
        AnalyticRepository saver = null;
        new MainMenuController(transactionService, saver).start();
    }
}
