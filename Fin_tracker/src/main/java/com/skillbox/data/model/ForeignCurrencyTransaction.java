package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForeignCurrencyTransaction extends Transaction implements CurrencyConvertible {

    private double exchangeRate;

    @Override
    public BigDecimal convertToBaseCurrency(BigDecimal amountInForeignCurrency) {
        return amountInForeignCurrency
                .multiply(BigDecimal.valueOf(exchangeRate))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public double getFinalAmount() {
        BigDecimal baseAmount = convertToBaseCurrency(BigDecimal.valueOf(getAmount()));
        return baseAmount.doubleValue();
    }
}
