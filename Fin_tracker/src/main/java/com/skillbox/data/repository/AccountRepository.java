package com.skillbox.data.repository;

import com.skillbox.data.model.Account;
import com.skillbox.exception.DataAccessException;

import java.util.List;

public interface AccountRepository {
    List<Account> readAll() throws DataAccessException;

    Account findById(int id);
}
