package com.skillbox.data.model;

public class RegularTransaction extends Transaction {
    @Override
    public double getFinalAmount() {
        return getAmount();
    }
}

