package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getAllLoad() {

        List<Loan> loans = loanRepository.findAll();

        Stream<Loan> loanStream = loans.stream();

        Stream<LoanDTO> loanDTOStream = loanStream.map(loan -> new LoanDTO(loan));

        List<LoanDTO> loanDTOS = loanDTOStream.collect(Collectors.toList());


        return loanDTOS;
    }


}
