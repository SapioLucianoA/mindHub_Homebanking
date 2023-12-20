package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Transaction {
    public void setAccount(Account account) {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private String description;
    private Double amount;

    private Double currentBalance;

    private Status status;

    public Transaction() {
    }


    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn()
    private Account account;

    public Transaction(Status status, Double currentBalance, LocalDateTime date, TransactionType type, String description, Double amount, Account account) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.account = account;
        this.currentBalance = currentBalance;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
