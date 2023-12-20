package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
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

import static com.mindhub.homebanking.models.Status.INACTIVE;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    // cuentas de todos
    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountService.findAllAccounts();
        return accounts.stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    //accounts x id
    @GetMapping("/accounts/{accountId}")
    public AccountDTO getAccountById(@PathVariable Long accountId) {
        Account account = accountService.findByAccountId(accountId);
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
    if (!accountService.accountExistByNumber(number)) {
        return new ResponseEntity<>("The origin account number does not exist", HttpStatus.FORBIDDEN);
    }
    if (!accountService.accountExistByNumber(number2)){
        return new ResponseEntity<>("The account to you want to send not exist", HttpStatus.FORBIDDEN);
    }
    if (amount <= 0){
        return new ResponseEntity<>("The amount cant be 0 or less", HttpStatus.FORBIDDEN);
    }

    //primero obtener el cliente
    //segundo buscar el cliente authenticado
    //buscar la cuenta de origen
    // ver si la cuenta de origen pertenece al cleinte
    //verificar el monto
        String email = authentication.getName();
        Client client = clientService.findClientByEmail(email);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        Account originAccount = accountService.findAccountByNumber(number);
        if (!originAccount.getClient().equals(client)){
            return new ResponseEntity<>("The origin account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount){
            return new ResponseEntity<>("The Amount cant be more than the balance of the account", HttpStatus.FORBIDDEN);
        }
        // llamado a services y lo demas necesario
        Account accountSend = accountService.findAccountByNumber(number2);

        //operacion para la transaction debo hacerlo antes por el set
        Double balanceOrigin  = originAccount.getBalance() - amount;
        Double balanceSend = accountSend.getBalance() + amount;
        //crear las weas de operacioness

        originAccount.setBalance(originAccount.getBalance() - amount);


        accountSend.setBalance(accountSend.getBalance() + amount);




        // crear dos transactiones number DEBIT y la number2 CREDIT
        Transaction transaction1 = new Transaction(Status.ACTIVE,balanceOrigin,LocalDateTime.now(), TransactionType.DEBIT,description,-amount,originAccount);
        Transaction transaction2 = new Transaction(Status.ACTIVE,balanceSend,LocalDateTime.now(), TransactionType.CREDIT, description, amount,accountSend);


// Guarda los cambios en las cuentas y transacciones
        clientService.saveTransaction(transaction1);
        clientService.saveTransaction(transaction2);
        accountService.saveAccount(originAccount);
        accountService.saveAccount(accountSend);

        return new ResponseEntity<>("transaction created", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/remove/account")
    public ResponseEntity<?> removeAccount (Authentication authentication,
                                            @RequestParam String number,
                                            @RequestParam Status status){
        String email = authentication.getName();
        Client client = clientService.findClientByEmail(email);
        if (!clientService.clientExistByEmail(email)){
            return new ResponseEntity<>("The client  dose not exist", HttpStatus.FORBIDDEN);
        }
        if (status == INACTIVE){
            return new ResponseEntity<>("The Account is INACTIVE", HttpStatus.FORBIDDEN);
        }
        if (!accountService.accountExistByNumber(number)){
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountService.accountExistByNumberAndClient(number, client)){
            return new ResponseEntity<>("The client is not the owner of the account", HttpStatus.FORBIDDEN);
        }


        Account account = accountService.findAccountByNumber(number);
        if (account.getBalance() > 0 )
        {

            return new ResponseEntity<>("the balance be 0 to remove a account", HttpStatus.FORBIDDEN);
        }
        List<Transaction> transactions = clientService.findAllTransactionsByAccount(account);

        for (Transaction transaction : transactions){
            transaction.setStatus(INACTIVE);

            clientService.saveTransaction(transaction);
        }
        account.setStatus(INACTIVE);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Account remove success", HttpStatus.CREATED);
    }










}


