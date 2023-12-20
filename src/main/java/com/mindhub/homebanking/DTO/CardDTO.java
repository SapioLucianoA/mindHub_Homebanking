package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Status;

import java.time.LocalDate;

public class CardDTO {
    private Long id;

    private CardType type;
    private CardColor color;
    private String cvv;
    private String cardHolder;
    private String number;
    private LocalDate fromDate;
    private LocalDate thruDate;

    private Status status;
    public CardDTO(Card card){
        id= card.getId();
        type = card.getType();
        color = card.getColor();
        cvv = card.getCvv();
        cardHolder= card.getCardHolder();
        number = card.getNumber();
        fromDate = card.getFromDate();
        thruDate = card.getThruDate();
        status = card.getStatus();
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public Status getStatus() {
        return status;
    }
}
