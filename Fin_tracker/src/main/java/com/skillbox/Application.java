package com.skillbox;

import com.skillbox.controller.MainMenuController;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.data.repository.impl.FileAccountRepository;
import com.skillbox.data.repository.impl.FileAnalyticRepository;
import com.skillbox.data.repository.impl.FileTransactionRepository;
import com.skillbox.service.TransactionService;
import com.skillbox.service.impl.TransactionServiceImpl;

public class Application {

    public static void main(String[] args) {
        // 1. Проверка аргументов (CLI)
        if (args.length < 3) {
            System.err.println("Ошибка: Нужно 3 аргумента (accounts.txt transactions.txt results.json)");
            System.exit(1);
        }

        String accountFilename = args[0];
        String transactionFilename = args[1];
        String outputFilename = args[2];

        // 2. Инициализация Репозиториев (DAL)
        AccountRepository accountReader = new FileAccountRepository(accountFilename);
        TransactionRepository transactionReader = new FileTransactionRepository(transactionFilename);
        AnalyticRepository saver = new FileAnalyticRepository(outputFilename);

        // 3. Инициализация Сервиса (BLL) - передаем репозитории внутрь
        TransactionService transactionService = new TransactionServiceImpl(transactionReader, accountReader);

        // 4. Запуск Контроллера (Presentation) - передаем сервис и сейвер
        // Раньше тут были null, из-за этого всё падало!
        new MainMenuController(transactionService, saver).start();
    }
}