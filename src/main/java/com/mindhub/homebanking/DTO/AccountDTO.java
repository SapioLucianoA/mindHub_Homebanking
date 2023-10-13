package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private LocalDate currentDateValue;
    private Double balance;
    private Long number;
    private List<TransactionDTO> transactions;


    public AccountDTO(Account account){
        id = account.getId();
        currentDateValue = account.getCurrentDate();
        balance = account.getBalance();
        number = account.getNumber();
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

    public Long getNumber() {
        return number;
    }
    public List<TransactionDTO> getTransactions() {
        return transactions;
    }


}
