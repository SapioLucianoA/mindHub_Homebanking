package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double amount;
    private Integer payments;
    private Integer sold;

    private  Status status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payments, Client client, Loan loan, Integer sold, Status status) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
        this.sold = sold;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public Integer getSold() {
        return sold;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }
    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    @JsonIgnore
    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

}
