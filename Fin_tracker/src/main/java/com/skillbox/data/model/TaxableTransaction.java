package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data // Генерирует геттеры и сеттеры, включая setTaxRate
@EqualsAndHashCode(callSuper = true)
public class TaxableTransaction extends Transaction implements Taxable {

    private double taxRate; // Поле для хранения налоговой ставки (например, 0.20)

    @Override
    public BigDecimal calculateTax() {
        // Округляем по правилам ТЗ
        return BigDecimal.valueOf(getAmount() * taxRate)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public double getFinalAmount() {
        return getAmount() - calculateTax().doubleValue();
    }
}
