package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAllClients();

    Client findClientById(Long id);

    Client findClientByEmail(String email);

    boolean clientExistById (Long id);

    boolean clientExistByEmail (String email);


    void saveClient (Client client);


  //Cards

    void saveCard (Card card);

    List <Card> findAllCards ();

    boolean existCardByNumber(String number);

    boolean existCardByNumberAndClient(String number, Client client);

    Card findCardByNumber (String number);
    boolean existCardByClientColorTypeAndStatus (Client client, CardColor color, CardType type, Status status);


    //Loan

    Loan findLoanById (Long id);

    List<Loan> findAllLoans ();

    //clientLoan

    ClientLoan findClientLoanByClientAndLoan (Client client, Loan loan);

    void saveClientLoan (ClientLoan clientLoan);

    List<ClientLoan> findAllClientLoans ();

    ClientLoan findClientLoanByClientIdAndLoanId(Long clientId, Long loanId);

    boolean existClientLoanByClientLoanAndStatus(Client client, Loan loan, Status status);


    // Transactions
    void saveTransaction (Transaction transaction);

    List<Transaction> findAllTransactions ();

    List<Transaction> findAllTransactionsByAccount(Account account);

    //LOAN

    void saveLoan (Loan loan);
    Loan findLoanByName(String name);



}
