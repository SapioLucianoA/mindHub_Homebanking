package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class NewLoanDTO {

    private String name;
    private Double maxAmount;

    private List<Integer> payment;

    public NewLoanDTO() {
    }

    public NewLoanDTO(Loan loan){
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payment = loan.getPayment();
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
}
