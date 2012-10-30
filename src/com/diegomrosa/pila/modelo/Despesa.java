package com.diegomrosa.pila.modelo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Despesa {
    private static final DateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    private Long id;
    private Date data;
    private Dinheiro valor;
    private Double quantidade;
    private String tags;

    public Despesa(Long id, Date data, Dinheiro valor, Double quantidade, String tags) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.quantidade = quantidade;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public Dinheiro getValor() {
        return valor;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public String getTags() {
        return tags;
    }

    public String getTotal() {
        Dinheiro val = (this.valor == null) ? Dinheiro.ZERO : this.valor;
        double qtde = (this.quantidade == null) ? 0.0 : this.quantidade;
        Dinheiro total = val.multiplica(qtde);

        return total.toString();
    }

    public String toString() {
        return FORMATO_DATA.format(data) + " - " + getTotal();
    }
}
