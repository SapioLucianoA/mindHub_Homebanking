package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.*;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByClientAndColorAndTypeAndStatus(Client client, CardColor color, CardType type, Status status);


   boolean existsByNumber(String  number);



   boolean existsByNumberAndClient(String number, Client client);


    Card findByNumber(String number);
}
