package com.tr.beyzanur.exchange;

import com.tr.beyzanur.exception.ExchangeNotFoundException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Rate {

    private double TRY;
    private double USD;
    private double EUR;

    public BigDecimal exchangeValueByCurrency(String currency) {

        if (currency.equals("USD")) {
            return BigDecimal.valueOf(getUSD());
        }
        if (currency.equals("TRY")) {
            return BigDecimal.valueOf(getTRY());
        }
        if (currency.equals("EUR")) {
            return BigDecimal.valueOf(getEUR());
        }
        else {
            throw new ExchangeNotFoundException("Exchange type not found");
        }
    }
}
