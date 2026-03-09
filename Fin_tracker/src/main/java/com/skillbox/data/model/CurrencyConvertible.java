package com.skillbox.data.model;

import java.math.BigDecimal;

/**
 * Интерфейс описывает транзакции, совершенные в иностранной валюте
 */
public interface CurrencyConvertible {

    /**
     * Метод переводит сумму в иностранной валюте в базовую валюту
     * @param amount сумма в иностранной валюте
     * @return сумма в базовой валюте
     */
    BigDecimal convertToBaseCurrency(BigDecimal amount);

}


