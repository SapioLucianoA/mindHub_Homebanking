package com.mindhub.homebanking.service;

import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public String generateUniqueAccountNumber() {
        Random rand = new Random();
        String num1;
        do {
            int num = rand.nextInt(99999999) + 1;
            num1 = String.format("%03d", num);
        } while(accountRepository.existsBynumber("VIN-" + num1));
        return "VIN-" + num1;
    }
}