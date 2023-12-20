package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {


    @Autowired
    private ClientService clientService;



    @GetMapping("/clientLoans")
    public List<ClientLoanDTO> getAllClientLoan(){
        List<ClientLoan> clientLoans = clientService.findAllClientLoans();
        return clientLoans.stream()
                .map(clientLoan -> new ClientLoanDTO(clientLoan))
                .collect(Collectors.toList());
    }

}
