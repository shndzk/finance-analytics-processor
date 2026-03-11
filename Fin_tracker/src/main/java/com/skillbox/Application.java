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
        System.setProperty("console.encoding", "UTF-8");
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
        if (args.length < 3) {
            System.err.println("Ошибка: Нужно 3 аргумента (accounts.txt transactions.txt results.json)");
            System.exit(1);
        }

        String accountFilename = args[0];
        String transactionFilename = args[1];
        String outputFilename = args[2];

        AccountRepository accountReader = new FileAccountRepository(accountFilename);
        TransactionRepository transactionReader = new FileTransactionRepository(transactionFilename);
        AnalyticRepository saver = new FileAnalyticRepository(outputFilename);

        TransactionService transactionService = new TransactionServiceImpl(transactionReader, accountReader);

        new MainMenuController(transactionService, saver).start();
    }
}