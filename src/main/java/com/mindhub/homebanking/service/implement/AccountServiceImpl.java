package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;


    @Override
    public boolean accountExistByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    public Account findAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account findByAccountId(Long id) {
        return accountRepository.findById(id).orElseThrow(null);
    }

    @Override
    public String generateAccountNumber() {
            Random rand = new Random();
            String num1;
            do {
                int num = rand.nextInt(99999999) + 1;
                num1 = String.format("%03d", num);
            } while(accountRepository.existsByNumber("VIN-" + num1));
            return "VIN-" + num1;
        }

    @Override
    public boolean accountExistByNumberAndClient(String number, Client client) {
        return accountRepository.existsByNumberAndClient(number, client);
    }


}
