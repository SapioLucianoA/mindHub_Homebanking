package com.mindhub.homebanking.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name;

    private String lastName;

    private String mail;

    private List <AccountDTO> accounts;
    @JsonManagedReference
    private List<ClientLoanDTO> loans;


    public ClientDTO (Client client){
      id = client.getId();
      name = client.getName();
      lastName = client.getLastName();
      mail = client.getMail();
      accounts = client.getAccounts()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
      loans= client.getClientLoans()
                     .stream()
                .map(clientLoan -> new ClientLoanDTO(clientLoan))
                  .collect(Collectors.toList());
    };

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }
    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getClientLoans() {
        return loans;
    }
}
