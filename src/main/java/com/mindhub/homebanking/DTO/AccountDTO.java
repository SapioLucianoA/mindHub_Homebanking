package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

import javax.xml.crypto.Data;
import java.time.LocalDate;

public class AccountDTO {

    private Long id;
    private LocalDate currentDateValue;
    private Long balance;
    private Long number;

    public AccountDTO(Account account){
        id = account.getId();
        currentDateValue = account.getCurrentDate();
        balance = account.getBalance();
        number = account.getNumber();

    }

    public Long getId() {
        return id;
    }

    public LocalDate getCurrentDate() {
        return currentDateValue;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getNumber() {
        return number;
    }

}
