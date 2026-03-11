package com.skillbox.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Transaction {
    private int accountId;
    private int id;
    private LocalDateTime dateTime;
    private String category;
    private double amount;

    public abstract double getFinalAmount();
}
