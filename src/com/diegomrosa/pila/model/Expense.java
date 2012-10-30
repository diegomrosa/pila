package com.diegomrosa.pila.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private Long id;
    private Date date;
    private Money value;
    private Double amount;
    private String tags;

    public Expense(Long id, Date date, Money value, Double amount, String tags) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.amount = amount;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Money getValue() {
        return value;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTags() {
        return tags;
    }

    public String getTotal() {
        Money val = (this.value == null) ? Money.ZERO : this.value;
        double qtde = (this.amount == null) ? 0.0 : this.amount;
        Money total = val.multiply(qtde);

        return total.toString();
    }

    public String toString() {
        return DATE_FORMAT.format(date) + " - " + getTotal();
    }
}
