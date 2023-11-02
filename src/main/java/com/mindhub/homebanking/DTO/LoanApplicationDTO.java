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

    public LoanApplicationDTO(Account account, ClientLoan clientLoan){
     loanId = clientLoan.getId();
     amount = clientLoan.getAmount();
    payment = clientLoan.getPayments();
    accountNumber= account.getNumber();
 }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
