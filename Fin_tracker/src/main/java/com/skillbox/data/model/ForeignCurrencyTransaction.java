package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForeignCurrencyTransaction extends Transaction implements CurrencyConvertible {

    private double exchangeRate; // Курс валюты из файла

    @Override
    public BigDecimal convertToBaseCurrency(BigDecimal amountInForeignCurrency) {
        // Умножаем сумму на курс и округляем до 2 знаков (как в примере: -45 * 85 = -3825.00)
        return amountInForeignCurrency
                .multiply(BigDecimal.valueOf(exchangeRate))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public double getFinalAmount() {
        // Переводим исходный double amount в BigDecimal, конвертируем и возвращаем результат
        BigDecimal baseAmount = convertToBaseCurrency(BigDecimal.valueOf(getAmount()));
        return baseAmount.doubleValue();
    }
}
