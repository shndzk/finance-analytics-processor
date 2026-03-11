package com.skillbox.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountImpl extends Account {

    private int accountId;
    private AccountType accountType;
    private int userId;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> transactions = new ArrayList<>();

    public AccountImpl(int accountId, AccountType accountType, int userId) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.userId = userId;
    }

    @Override
    public int getAccountId() { return accountId; }

    @Override
    public int getUserId() { return userId; }

    @Override
    public AccountType getAccountType() { return accountType; }

    @Override
    public BigDecimal getBalance() { return balance; }

    @Override
    public void addTransaction(Transaction transaction) { this.transactions.add(transaction); }

    @Override
    public List<Transaction> getTransactions() { return transactions; }
}
