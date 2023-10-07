package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDate currentDateValue;
    private Long balance;
    private Long number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {
    }

    public Account(LocalDate currentDate, Long balance, Long number, Client client) {
        this.currentDateValue = currentDate;
        this.balance = balance;
        this.number = number;
        this.client = client;
    }


    public Long getId() {
        return id;
    }

    public LocalDate getCurrentDate() {
        return currentDateValue;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDateValue = currentDate;
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

}
