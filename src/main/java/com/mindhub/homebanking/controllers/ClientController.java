package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CardsService cardsService;
    @Autowired
    private CardRepository cardRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getAllClient() {

        List<Client> clients = clientRepository.findAll();

        Stream<Client> clientStream = clients.stream();

        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));

        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());


        return clientDTOS;
    };


    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String name, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (name.isBlank()){
            return new ResponseEntity<>("Missing Name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()){
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()){
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if (password.isBlank()){
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password),ClientRole.CLIENT);

        String accountNumber = accountService.generateUniqueAccountNumber();

        Account account = new Account(LocalDate.now(), 0.00, accountNumber, client);

        clientRepository.save(client);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        System.out.println(authentication);
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createdAccount(Authentication authentication){

        String email = authentication.getName();

        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("you cant have more Accounts", HttpStatus.FORBIDDEN);
        }

        // metodos de service y demas
        String accountNumber = accountService.generateUniqueAccountNumber();

        //creacion y guardado
        Account account = new Account(LocalDate.now(), 0.00, accountNumber, client);

        accountRepository.save(account);
        return new ResponseEntity<>("Account created success!",HttpStatus.CREATED);

    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard (Authentication authentication, @RequestParam CardType type,
@RequestParam CardColor color, @RequestParam String name, @RequestParam String lastName) {

        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }


        Set<Card> cards = client.getCards();

        // Obtén las tarjetas del cliente
        long count = cards.stream().filter(card -> card.getType().equals(type)).count();

        // Verifica si ya existe una tarjeta del mismo tipo
        if(count >= 3){
            return new ResponseEntity<>("Client already has 3 cards of this type", HttpStatus.FORBIDDEN);
        }
        // metodos de service y demas

        String cvv = cardsService.generateCVV();
        String number = cardsService.generateCardNumber();
        String cardHolder = name + " " + lastName;

        //creacion y guardado
        Card card = new Card(client, type, color, cvv, cardHolder, number, LocalDate.now(), LocalDate.now().plusYears(5));
        cardRepository.save(card);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }


    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            return new ClientDTO(client);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
    }

    //@PostMapping("/Clients")
    //public Client create(@RequestBody Client client) {
     //   return clientRepository.save(client);
    //};
}
    /*@PutMapping("/api/clients/{id}")
    public Client update(@RequestBody Client client, @PathVariable Long id ){
        Client clientBase = clientRepository.getOne(id);
        clientBase.setName(client.getName());
        clientBase.setLastName(client.getLastName());
        clientBase.setMail(client.getMail());

        return clientRepository.save(client);
    }

    @DeleteMapping("/api/client/{id}")
    public void delete(@PathVariable Long id){
        clientRepository.deleteById(id);
    }


}*/
