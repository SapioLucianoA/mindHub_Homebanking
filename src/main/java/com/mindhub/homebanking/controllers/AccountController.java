package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // cuentas de todos
    @RequestMapping("/accounts")
    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    //accounts x id
    @GetMapping("/accounts/{accountId}")
    public AccountDTO getAccountById(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));
        return new AccountDTO(account);
    };

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> sendTransaction (
            Authentication authentication, @RequestParam Double amount, @RequestParam String number,
            @RequestParam String number2, @RequestParam String description
    ){
    if (amount.isNaN()){
        return new ResponseEntity<>("Please put a amount valid", HttpStatus.FORBIDDEN);
    }
    if (number.isBlank()){
        return new ResponseEntity<>("Missing origen account", HttpStatus.FORBIDDEN);
    }
    if (number2.isBlank()){
            return new ResponseEntity<>("Missing account to send", HttpStatus.FORBIDDEN);
        }
    if (description.isBlank()){
        return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
    }
    if (number.equals(number2)){
        return new ResponseEntity<>("The originating account can`t be the same as the account of the receiver", HttpStatus.FORBIDDEN);
    }
    if (accountRepository.findByNumber(number) == null) {
        return new ResponseEntity<>("The origin account number does not exist", HttpStatus.FORBIDDEN);
    }
    if (accountRepository.findByNumber(number2) == null){
        return new ResponseEntity<>("The account to you want to send not exist", HttpStatus.FORBIDDEN);
    }

    //primero obtener el cliente
    //segundo buscar el cliente authenticado
    //buscar la cuenta de origen
    // ver si la cuenta de origen pertenece al cleinte
    //verificar el monto
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        Account originAccount = accountRepository.findByNumber(number);
        if (!originAccount.getClient().equals(client)){
            return new ResponseEntity<>("The origin account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount){
            return new ResponseEntity<>("The Amount cant be more than the balance of the account", HttpStatus.FORBIDDEN);
        }
        // llamado a services y lo demas necesario
        Account accountSend = accountRepository.findByNumber(number2);

        // crear dos transactiones number DEBIT y la number2 CREDIT
        Transaction transaction1 = new Transaction(LocalDateTime.now(), TransactionType.DEBIT,description,-amount,originAccount);
        Transaction transaction2 = new Transaction(LocalDateTime.now(), TransactionType.CREDIT, description, amount,accountSend);

        //crear las weas de operacioness

        originAccount.setBalance(originAccount.getBalance() - amount);


        accountSend.setBalance(accountSend.getBalance() + amount);

// Guarda los cambios en las cuentas y transacciones
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        accountRepository.save(originAccount);
        accountRepository.save(accountSend);

        return new ResponseEntity<>("transaction created", HttpStatus.CREATED);
    }











}


