package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDate currentDateValue;
    private Double balance;
    private String number;
    private AccountClass accountClass;
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();



    public Account() {
    }

    public Account(LocalDate currentDate, Double balance, String number, Client client, AccountClass accountClass, Status status) {
        this.currentDateValue = currentDate;
        this.balance = balance;
        this.number = number;
        this.client = client;
        this.accountClass = accountClass;
        this.status = status;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public AccountClass getAccountClass() {
        return accountClass;
    }

    public LocalDate getCurrentDateValue() {
        return currentDateValue;
    }

    public void setCurrentDateValue(LocalDate currentDateValue) {
        this.currentDateValue = currentDateValue;
    }

    public void setAccountClass(AccountClass accountClass) {
        this.accountClass = accountClass;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonBackReference
    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", currentDateValue='" + currentDateValue + '\'' +
                ", balance='" + balance + '\'' +
                ", number='" + number + '\'' +
                ", accountClass='" + accountClass + '\'' +
                ", status='" + status + '\'' +
                ", client='" + client + '\'' +
                '}';
    }

}
