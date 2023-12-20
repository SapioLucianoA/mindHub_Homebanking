package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;

public class LoanApplicationDTO {
    private Long loanId;
    private Double amount;
    private Integer payment;
    private String accountNumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Double amount, Integer payment, String accountNumber) {
        this.amount = amount;
        this.payment = payment;
        this.accountNumber = accountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
