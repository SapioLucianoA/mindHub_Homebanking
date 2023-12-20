package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.models.*;

import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardUtils;

import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mindhub.homebanking.models.AccountClass.GENERIC;
import static com.mindhub.homebanking.models.Status.ACTIVE;
import static com.mindhub.homebanking.models.Status.INACTIVE;

@RestController
@RequestMapping("/api")
public class ClientController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;








    @GetMapping("/clients")
    public List<ClientDTO> getAllClient() {

        List<Client> clients = clientService.findAllClients();

        Stream<Client> clientStream = clients.stream();

        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));

        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());


        return clientDTOS;
    }

    ;


    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String name, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (name.isBlank()) {
            return new ResponseEntity<>("Missing Name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if (password.isBlank()) {
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }

        if (clientService.clientExistByEmail(email)) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password), ClientRole.CLIENT);

        String accountNumber = accountService.generateAccountNumber();

        Account account = new Account(LocalDate.now(), 0.00, accountNumber,client, GENERIC,ACTIVE);

        clientService.saveClient(client);
        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {

        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createdAccount(Authentication authentication, @RequestParam AccountClass accountClass) {

        String email = authentication.getName();

        Client client = clientService.findClientByEmail(email);
        Set<Account> accounts = client.getAccounts();
        List<Account> accountList = new ArrayList<>(accounts);
        List<Account> accountsFilter = accountList.stream()
                .filter(account -> account.getStatus() == ACTIVE)
                .collect(Collectors.toList());


        if (accountsFilter.size() >= 3) {
            return new ResponseEntity<>("you cant have 3 or more Accounts", HttpStatus.FORBIDDEN);
        }

        // metodos de service y demas
        String accountNumber = accountService.generateAccountNumber();;

        //creacion y guardado
        Account account = new Account(LocalDate.now(), 0.00, accountNumber, client, accountClass,ACTIVE);

        accountService.saveAccount(account);
        return new ResponseEntity<>("Account created success!", HttpStatus.CREATED);

    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> currentAccounts(Authentication authentication) {
        String email = authentication.getName();

        Client client = clientService.findClientByEmail(email);
        Set<Account> accountSet = client.getAccounts();
        List<Account> accounts = new ArrayList<>(accountSet);

        return accounts.stream().filter(account -> account.getStatus() != null && account.getStatus() == ACTIVE)
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }



    @GetMapping("/clients/current/cards")
    public List<CardDTO> currentCards(Authentication authentication) {
        String email = authentication.getName();

        Client client = clientService.findClientByEmail(email);
        Set<Card> cardSet = client.getCards();
        List<Card> cards = new ArrayList<>(cardSet);

        return cards.stream()
                .map(card -> new CardDTO(card))
                .filter(cardDTO -> cardDTO.getStatus() != null && cardDTO.getStatus().equals(ACTIVE))
                .collect(Collectors.toList());
    }


    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        if (client != null) {
            return new ClientDTO(client);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
    }





    @PostMapping("/client/admin")
    public ResponseEntity<Object> createAdmin(@RequestParam String name, @RequestParam String lastName,
                                              @RequestParam String email, @RequestParam String password,
                                              @RequestParam ClientRole clientRole) {
        if (name.isBlank()) {
            return new ResponseEntity<>("Missing Name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if (password.isBlank()) {
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }

        if (clientService.clientExistByEmail(email)) {
            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);
        }
        if (clientRole == null){
            return new ResponseEntity<>("no role assigned", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(name, lastName, email, password, clientRole);
        clientService.saveClient(client);
        return new ResponseEntity<>("new client created", HttpStatus.CREATED);
    }

    ;




}

  /*  @DeleteMapping("/api/client/{email}")
    public void delete(@PathVariable String email){
        clientRepository.deleteById(email);
    }


}*/
