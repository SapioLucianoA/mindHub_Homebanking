package com.mindhub.homebanking.models;

import jdk.jfr.DataAmount;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Transaction {
    public void setAccount(Account account) {
    }

    public enum TransactionType {
        DEBIT,
        CREDIT
    };
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalTime date;
    private TransactionType type;
    private String description;
    private Double amount;

    public Transaction() {
    }

    public Transaction(LocalTime date, TransactionType type, String description, Double amount) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn()
    private Account account;

    public Long getId() {
        return id;
    }

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
