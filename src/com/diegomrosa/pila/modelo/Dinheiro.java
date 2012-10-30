package com.diegomrosa.pila.modelo;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Dinheiro {
    private static final BigDecimal FATOR_CORRECAO = new BigDecimal(1000000);
    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static final Dinheiro ZERO = new Dinheiro(0L);

    private long valor;

    public Dinheiro(long valor) {
        this.valor = valor;
    }

    public long longValue() {
        return valor;
    }

    public Dinheiro multiplica(double quantidade) {
        BigDecimal valorBig = bigDecimalValue();
        BigDecimal qtdeBig = new BigDecimal(quantidade);

        return new Dinheiro(valorBig.multiply(qtdeBig).longValue());
    }

    public static Dinheiro parse(String dinheiroStr) {
        BigDecimal dinheiroBig = null;

        try {
            dinheiroBig = new BigDecimal(dinheiroStr);
        } catch (NumberFormatException exc) {
            dinheiroBig = BigDecimal.ZERO;
        }
        return new Dinheiro(dinheiroBig.multiply(FATOR_CORRECAO).longValue());
    }

    public String toString() {
        BigDecimal dinheiroBig = bigDecimalValue();

        return FORMATO_MOEDA.format(dinheiroBig.divide(FATOR_CORRECAO));
    }

    public BigDecimal bigDecimalValue() {
        return new BigDecimal(this.valor);
    }
}
