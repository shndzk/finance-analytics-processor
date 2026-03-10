package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaxableTransaction extends Transaction implements Taxable {

    private double taxRate;

    @Override
    public BigDecimal calculateTax() {
        return BigDecimal.valueOf(getAmount() * taxRate)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public double getFinalAmount() {
        return getAmount() - calculateTax().doubleValue();
    }
}
