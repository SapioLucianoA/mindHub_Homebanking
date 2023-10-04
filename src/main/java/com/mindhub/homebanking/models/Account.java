package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.xml.crypto.Data;

@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Data currentDate;
    private Long balance;
    private Long number;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    public Account() {
    }

    public Account(Data currentDate, Long balance, Long number) {
        this.currentDate = currentDate;
        this.balance = balance;
        this.number = number;

    }

    public Long getId() {
        return id;
    }

    public Data getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Data currentDate) {
        this.currentDate = currentDate;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
    public void setClient(Client client) {
        this.client = client;
    }

}
