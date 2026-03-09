package com.skillbox.data.model;

// 1. Regular
public class RegularTransaction extends Transaction {
    @Override
    public double getFinalAmount() {
        return getAmount(); // Теперь метод существует в базовом классе
    }
}

