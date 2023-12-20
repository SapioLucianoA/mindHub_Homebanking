package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {

    boolean accountExistByNumber (String number);

    Account findAccountByNumber (String number);
    void saveAccount (Account account);

    List<Account> findAllAccounts();

    Account findByAccountId(Long id);

    String generateAccountNumber();

    boolean accountExistByNumberAndClient(String number, Client client);

}
