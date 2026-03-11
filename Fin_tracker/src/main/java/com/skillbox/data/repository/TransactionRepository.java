package com.skillbox.data.repository;

import com.skillbox.data.model.Transaction;
import com.skillbox.exception.DataAccessException;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> readAll() throws DataAccessException;
}
