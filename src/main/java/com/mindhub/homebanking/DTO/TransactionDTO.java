package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transaction;

import java.time.LocalTime;

public class TransactionDTO {
    private Long id;
    private LocalTime date;
    private Transaction.TransactionType type;
    private String description;
    private Double amount;

    public TransactionDTO(Transaction transaction){
        id = transaction.getId();
        date= transaction.getDate();
        type= transaction.getType();
        amount= transaction.getAmount();
    }

    public Long getId() {
        return id;
    }

    public LocalTime getDate() {
        return date;
    }

    public Transaction.TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }
}
