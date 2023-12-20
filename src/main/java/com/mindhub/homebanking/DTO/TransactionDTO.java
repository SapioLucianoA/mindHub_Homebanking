package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Status;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TransactionDTO {
    private Long id;
    private LocalDateTime date;

    private TransactionType type;

    private String description;
    private Double amount;
    private Double currentBalance;
    private Status status;

    public TransactionDTO(Transaction transaction){
        id = transaction.getId();
        date= transaction.getDate();
        description= transaction.getDescription();
        type= transaction.getType();
        amount= transaction.getAmount();
        currentBalance = transaction.getCurrentBalance();
        status = transaction.getStatus();
    }

    public Status getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }



    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }
    public Double getCurrentBalance(){return currentBalance;}
}
