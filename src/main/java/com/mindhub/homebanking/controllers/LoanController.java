package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.DTO.NewLoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardUtils;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mindhub.homebanking.models.Status.ACTIVE;
import static com.mindhub.homebanking.models.Status.INACTIVE;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;


    @Autowired
    private AccountService accountService;


    @GetMapping("/loans")
    public List<LoanDTO> getAllLoad() {

        List<Loan> loans = clientService.findAllLoans();

        Stream<Loan> loanStream = loans.stream();

        Stream<LoanDTO> loanDTOStream = loanStream.map(loan -> new LoanDTO(loan));

        List<LoanDTO> loanDTOS = loanDTOStream.collect(Collectors.toList());


        return loanDTOS;
    }

    @PostMapping("/loan/new")
    public ResponseEntity<Object> newLoan (Authentication authentication, @RequestBody NewLoanDTO loanDTO){
        String email = authentication.getName();

        Client client = clientService.findClientByEmail(email);

        if (client.getClientRole() != ClientRole.ADMIN){
            return new ResponseEntity<>("method only for ADMIN",HttpStatus.FORBIDDEN);
        }
        if (loanDTO.getName().isBlank()){
            return new ResponseEntity<>("please complete the name", HttpStatus.FORBIDDEN);
        }
        if (loanDTO.getMaxAmount().isNaN()){
            return new ResponseEntity<>("please complete the amount", HttpStatus.FORBIDDEN);
        }
        if (loanDTO.getMaxAmount() <= 0){
            return new ResponseEntity<>("The Max Amount cant be 0 or less", HttpStatus.FORBIDDEN);
        }
        if (loanDTO.getPayment().isEmpty()){
            return new ResponseEntity<>("Please complete the list od payment", HttpStatus.FORBIDDEN);
        }
        Loan loan = new Loan(loanDTO.getName(), loanDTO.getMaxAmount(), loanDTO.getPayment());

        clientService.saveLoan(loan);
        return new ResponseEntity<>("New Loan created", HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("/loan")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Loan loan = clientService.findLoanById(loanApplicationDTO.getLoanId());
        if (loan == null) {
            return new ResponseEntity<>("The Loan does not exist", HttpStatus.FORBIDDEN);
        }


        if (!accountService.accountExistByNumber(loanApplicationDTO.getAccountNumber())) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }


        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("Your amount cant be more than the Loan Max Amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("The amount can`t be 0 or less", HttpStatus.FORBIDDEN);
        }
        String email = authentication.getName();
        Client client = clientService.findClientByEmail(email);
        Account loanAccount = accountService.findAccountByNumber(loanApplicationDTO.getAccountNumber());
        if (!loanAccount.getClient().equals(client)){
            return new ResponseEntity<>("The client is not the propiertary of the account", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayment().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("The payment does not exist ", HttpStatus.FORBIDDEN);
        }

        if (clientService.existClientLoanByClientLoanAndStatus(client, loan, ACTIVE)){
            return new ResponseEntity<>("The client already has a loan of this type", HttpStatus.FORBIDDEN);
        }



        double amount1 = CardUtils.calculatedAmount(loanApplicationDTO.getPayment(), loanApplicationDTO.getAmount());

        Double balanceLoan = loanAccount.getBalance() + loanApplicationDTO.getAmount();

        Transaction loanTransaction  = new Transaction(ACTIVE,balanceLoan,LocalDateTime.now(), TransactionType.CREDIT, "Loan approved", loanApplicationDTO.getAmount(), loanAccount);

        ClientLoan clientLoan1 = new ClientLoan(amount1, loanApplicationDTO.getPayment(), client, loan, 0, ACTIVE);

        loanAccount.setBalance(loanAccount.getBalance() + loanApplicationDTO.getAmount());
        clientService.saveTransaction(loanTransaction);
        clientService.saveClientLoan(clientLoan1);
        accountService.saveAccount(loanAccount);
        return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);

    }

    @Transactional
    @PostMapping("/client/pay")
    public ResponseEntity<Object> payLoan(Authentication authentication,@RequestParam String number,
                                          @RequestParam Double amount,
                                          @RequestParam String loanName) {

        String email = authentication.getName();

        Client client = clientService.findClientByEmail(email);
        Loan loan = clientService.findLoanByName(loanName);
        ClientLoan clientLoan = clientService.findClientLoanByClientIdAndLoanId(client.getId(), loan.getId());
        Account account = accountService.findAccountByNumber(number);

        if (!clientService.clientExistByEmail(email)){
            return new ResponseEntity<>("The client dosent exist", HttpStatus.FORBIDDEN);
        }
        if (!accountService.accountExistByNumber(number)){
            return new ResponseEntity<>("The account dosent exist", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("The amount cant be 0 nor less", HttpStatus.FORBIDDEN);
        }
        if (clientLoan == null){
            return new ResponseEntity<>("The loan dosent exist", HttpStatus.FORBIDDEN);
        }
        if ( account.getBalance() < amount){
            return  new ResponseEntity<>("The account balance is less than the payment", HttpStatus.FORBIDDEN);
        }
        if (clientLoan.getPayments().equals(clientLoan.getSold())){
            clientLoan.setStatus(INACTIVE);
            return new ResponseEntity<>("The Loan is fully paid", HttpStatus.FORBIDDEN);
        }

        Double balanceTransaction = account.getBalance() - amount;
        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction(ACTIVE, balanceTransaction, LocalDateTime.now(), TransactionType.DEBIT, "LOAN PAYMENT", amount
                , account);


        clientLoan.setSold(clientLoan.getSold() + 1);


        clientService.saveTransaction(transaction);
        clientService.saveClientLoan(clientLoan);
        accountService.saveAccount(account);

        return new ResponseEntity<>("client Loan Update", HttpStatus.CREATED);
    }

}
