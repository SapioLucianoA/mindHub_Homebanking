package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardUtils;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.Status.ACTIVE;
import static com.mindhub.homebanking.models.Status.INACTIVE;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @GetMapping("/cards")
    public List<CardDTO> getAllCards(){
        List<Card> cards = clientService.findAllCards();
        return cards.stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(Authentication authentication, @RequestParam CardType type,
                                              @RequestParam CardColor color, @RequestParam String name, @RequestParam String lastName) {

        String email = authentication.getName();
        Client client = clientService.findClientByEmail(email);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if (clientService.existCardByClientColorTypeAndStatus(client, color, type, ACTIVE)){
            return new ResponseEntity<>("this Card already created", HttpStatus.FORBIDDEN);
        }


        Set<Card> cards = client.getCards();

        // Obtén las tarjetas del cliente
        long count = cards.stream().filter(card -> card.getType().equals(type)).filter(card -> card.getStatus() == ACTIVE).count();

        // ya tenés 3 papu
        if (count >= 3) {
            return new ResponseEntity<>("Client already has 3 cards of this type", HttpStatus.FORBIDDEN);
        }
        // metodos de service y demas

        String cvv = CardUtils.generateCVV();
        String number = CardUtils.generateCardNumber();
        String cardHolder = name + " " + lastName;

        //creacion y guardado
        Card card = new Card(Status.ACTIVE,client, type, color, cvv, cardHolder, number, LocalDate.now(), LocalDate.now().plusYears(5));
        clientService.saveCard(card);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }
    @PatchMapping("/client/remove/card")
    public ResponseEntity<Object> removeCard(Authentication authentication, @RequestParam String number,
                                             @RequestParam Status status){
        String email = authentication.getName();
        Client client = clientService.findClientByEmail(email);
        if (!clientService.existCardByNumber(number)){
            return new ResponseEntity<>("The Card dosent exist", HttpStatus.FORBIDDEN);
        }
        if (!clientService.clientExistByEmail(email)){
            return new ResponseEntity<>("The CLient  dosent exist", HttpStatus.FORBIDDEN);
        }
        if (!clientService.existCardByNumberAndClient(number, client)){
            return new ResponseEntity<>("The client is not the CardHolder", HttpStatus.FORBIDDEN);
        }
        if (status == INACTIVE){
            return new ResponseEntity<>("The Card is INACTIVE", HttpStatus.FORBIDDEN);
        }
        Card cardR = clientService.findCardByNumber(number);

        cardR.setStatus(INACTIVE);

        clientService.saveCard(cardR);

        return new ResponseEntity<>("The Card is removed for your account", HttpStatus.CREATED);
    }


}
