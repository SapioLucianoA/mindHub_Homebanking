package com.mindhub.homebanking.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClientLoanDTO {

    private Long id;
    private Double amount;
    private Integer payments;
    @JsonBackReference
    private Client client;

    private Long loanId;
    private String name;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        amount= clientLoan.getAmount();
        payments = clientLoan.getPayments();
        client= clientLoan.getClient();
        loanId = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
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
