package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardsService;
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

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getAllClient() {

        List<Client> clients = clientRepository.findAll();

        Stream<Client> clientStream = clients.stream();

        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));

        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());


        return clientDTOS;
    }

    ;


    @RequestMapping(path = "/clients", method = RequestMethod.POST)
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

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password), ClientRole.CLIENT);

        String accountNumber = accountService.generateUniqueAccountNumber();

        Account account = new Account(LocalDate.now(), 0.00, accountNumber, client);

        clientRepository.save(client);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        System.out.println(authentication);
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createdAccount(Authentication authentication) {

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
        return new ResponseEntity<>("Account created success!", HttpStatus.CREATED);

    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> currentAccounts(Authentication authentication) {
        String email = authentication.getName();

        Client client = clientRepository.findByEmail(email);
        Set<Account> accountSet = client.getAccounts();
        List<Account> accounts = new ArrayList<>(accountSet);

        return accounts.stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(Authentication authentication, @RequestParam CardType type,
                                              @RequestParam CardColor color, @RequestParam String name, @RequestParam String lastName) {

        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }


        Set<Card> cards = client.getCards();

        // Obtén las tarjetas del cliente
        long count = cards.stream().filter(card -> card.getType().equals(type)).count();

        // ya tenés 3 papu
        if (count >= 3) {
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

    @GetMapping("/clients/current/cards")
    public List<CardDTO> currentCards(Authentication authentication) {
        String email = authentication.getName();

        Client client = clientRepository.findByEmail(email);
        Set<Card> cardSet = client.getCards();
        List<Card> cards = new ArrayList<>(cardSet);

        return cards.stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
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


    @Transactional
    @PostMapping("/loan")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Optional<Loan> loan = loanRepository.findById(loanApplicationDTO.getLoanId());
        if (!loan.isPresent()) {
            return new ResponseEntity<>("The Loan does not exist", HttpStatus.FORBIDDEN);
        }


        if (!accountRepository.existsByNumber(loanApplicationDTO.getAccountNumber())) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }


        if (loanApplicationDTO.getAmount() > loan.get().getMaxAmount()){
            return new ResponseEntity<>("Your amount cant be more than the Loan Max Amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("The amount can`t be 0 or less", HttpStatus.FORBIDDEN);
        }
        String email = authentication.getName();
        Client client = clientRepository.findByEmail(email);
        Account loanAccount = accountRepository.findByNumber(loanApplicationDTO.getAccountNumber());
        if (!loanAccount.getClient().equals(client)){
            return new ResponseEntity<>("The client is not the propiertary of the account", HttpStatus.FORBIDDEN);
        }
        if (!loan.get().getPayment().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("The payment does not exist ", HttpStatus.FORBIDDEN);
        }

        Loan loan1 = loan.get();
        Optional<ClientLoan> existingLoan = clientLoanRepository.findByClientAndLoan(client, loan1);
        if (existingLoan.isPresent()){
            return new ResponseEntity<>("The client already has a loan of this type", HttpStatus.FORBIDDEN);
        }


        Double amount20 = loanApplicationDTO.getAmount()*1.2;

        Transaction loanTransaction  = new Transaction(LocalDateTime.now(), TransactionType.CREDIT, "Loan approved", loanApplicationDTO.getAmount(), loanAccount);
        ClientLoan clientLoan1 = new ClientLoan(amount20, loanApplicationDTO.getPayment(), client, loan1);

        loanAccount.setBalance(loanAccount.getBalance() + loanApplicationDTO.getAmount());
        transactionRepository.save(loanTransaction);
        clientLoanRepository.save(clientLoan1);
        accountRepository.save(loanAccount);
        return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);

    }


    @PostMapping("/Clients/admin")
    public ResponseEntity<Object> createAdmin(@RequestBody Client client) {
        if (client.getName().isBlank()) {
            return new ResponseEntity<>("Missing Name", HttpStatus.FORBIDDEN);
        }
        if (client.getLastName().isBlank()) {
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if (client.getEmail().isBlank()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if (client.getPassword().isBlank()) {
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(client.getEmail()) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        if (client.getClientRole() == null){
            return new ResponseEntity<>("no role assigned", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(client);
        return new ResponseEntity<>("new client created", HttpStatus.CREATED);
    }

    ;
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
