package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private CardType type;
    private CardColor color;
    private String cvv;
    private String cardHolder;
    private String number;
    private LocalDate fromDate;
    private LocalDate thruDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private Client client;

    public Card() {
    }

    public Card(Client client, CardType type, CardColor color, String cvv, String cardHolder, String number, LocalDate fromDate, LocalDate thruDate) {
        this.type = type;
        this.color = color;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.number = number;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
