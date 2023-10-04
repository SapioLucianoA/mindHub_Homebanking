package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

import javax.xml.crypto.Data;

public class AccountDTO {

    private Long id;
    private Data currentDate;
    private Long balance;
    private Long number;

    public AccountDTO(Account account){
        id = account.getId();
        currentDate = account.getCurrentDate();
        balance = account.getBalance();
        number = account.getNumber();

    }

    public Long getId() {
        return id;
    }

    public Data getCurrentDate() {
        return currentDate;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getNumber() {
        return number;
    }
}
