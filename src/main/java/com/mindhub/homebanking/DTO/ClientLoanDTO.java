package com.mindhub.homebanking.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.mindhub.homebanking.models.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClientLoanDTO {

    private Long id;
    private Double amount;
    private Integer payments;
    private Integer sold;
    @JsonBackReference
    private Client client;

    private Long loanId;
    private String name;
    private Status status;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        amount= clientLoan.getAmount();
        payments = clientLoan.getPayments();
        client= clientLoan.getClient();
        loanId = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        sold = clientLoan.getSold();
        status = clientLoan.getStatus();
    }

    public Integer getSold() {
        return sold;
    }

    public Status getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public Long getLoanId() {
        return loanId;
    }
    public String getName(){return name;};
}
