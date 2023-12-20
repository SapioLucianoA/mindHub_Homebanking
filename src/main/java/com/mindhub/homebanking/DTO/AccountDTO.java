package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountClass;
import com.mindhub.homebanking.models.Status;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private LocalDate currentDateValue;
    private Double balance;
    private String number;
    private List<TransactionDTO> transactions;
    private AccountClass accountClass;
    private Status status;


    public AccountDTO(Account account){
        id = account.getId();
        currentDateValue = account.getCurrentDate();
        balance = account.getBalance();
        number = account.getNumber();
        accountClass = account.getAccountClass();
        status = account.getStatus();
        transactions = account.getTransactions()
                        .stream()
                        .map(transaction -> new TransactionDTO(transaction))
                        .collect(Collectors.toList());

     };

    public Long getId() {
        return id;
    }

    public LocalDate getCurrentDate() {
        return currentDateValue;
    }

    public Double getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }
    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public AccountClass getAccountClass() {
        return accountClass;
    }

    public LocalDate getCurrentDateValue() {
        return currentDateValue;
    }

    public Status getStatus() {
        return status;
    }
}
