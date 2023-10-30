package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long id) {
        Account account;
        account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
        List<Transaction> transactions = transactionRepository.findByAccountId(account.getId());
        return ResponseEntity.ok(transactions);
    }


}
