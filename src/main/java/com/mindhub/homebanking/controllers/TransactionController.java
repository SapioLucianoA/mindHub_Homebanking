package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
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
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(){
        List<Transaction> transactions = clientService.findAllTransactions();
        return transactions.stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toList());
    }



}
