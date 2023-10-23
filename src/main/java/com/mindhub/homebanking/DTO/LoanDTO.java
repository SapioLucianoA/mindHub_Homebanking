package com.mindhub.homebanking.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mindhub.homebanking.models.Loan;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount;

    private List<Integer> payment;
    @JsonManagedReference
    private List<ClientLoanDTO> loans;

    public LoanDTO(Loan loan){
        id= loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payment = loan.getPayment();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayment() {
        return payment;
    }

    public List<ClientLoanDTO> getClientLoans() {
        return loans;
    }
}
