package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardContoller {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/accounts")
    public List<CardDTO> getAllCards(){
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }

}
