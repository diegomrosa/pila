package com.diegomrosa.pila.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Money {
    private static final BigDecimal FACTOR = new BigDecimal(1000000);
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static final Money ZERO = new Money(0L);

    private long value;

    public Money(long value) {
        this.value = value;
    }

    public long longValue() {
        return value;
    }

    public Money multiply(double amount) {
        BigDecimal bigValue = bigDecimalValue();
        BigDecimal bigAmount = new BigDecimal(amount);

        return new Money(bigValue.multiply(bigAmount).longValue());
    }

    public static Money parse(String moneyStr) {
        BigDecimal bigMoney = null;

        try {
            bigMoney = new BigDecimal(moneyStr);
        } catch (NumberFormatException exc) {
            bigMoney = BigDecimal.ZERO;
        }
        return new Money(bigMoney.multiply(FACTOR).longValue());
    }

    public String toString() {
        BigDecimal bigMoney = bigDecimalValue();

        return CURRENCY_FORMAT.format(bigMoney.divide(FACTOR));
    }

    public BigDecimal bigDecimalValue() {
        return new BigDecimal(this.value);
    }
}
