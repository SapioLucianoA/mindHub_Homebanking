package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClientServiceImpl implements ClientService {

        @Autowired
    private ClientRepository clientRepository;


        @Autowired
        private CardRepository cardRepository;

        @Autowired
        private LoanRepository loanRepository;

        @Autowired
        private ClientLoanRepository clientLoanRepository;

        @Autowired
        private TransactionRepository transactionRepository;


    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(null);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public boolean clientExistById(Long id) {
        return clientRepository.existsById(id);
    }

    @Override
    public boolean clientExistByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }



    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public boolean existCardByNumber(String number) {
        return cardRepository.existsByNumber(number);
    }

    @Override
    public boolean existCardByNumberAndClient(String number, Client client) {
        return cardRepository.existsByNumberAndClient(number, client);
    }

    @Override
    public Card findCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public boolean existCardByClientColorTypeAndStatus(Client client, CardColor color, CardType type, Status status) {
        return cardRepository.existsByClientAndColorAndTypeAndStatus(client, color,type,status);
    }

    @Override
    public Loan findLoanById(Long id) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        return optionalLoan.orElse(null);
    }


    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public ClientLoan findClientLoanByClientAndLoan(Client client, Loan loan) {
        return null;
    }


    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
            clientLoanRepository.save(clientLoan);
    }

    @Override
    public List<ClientLoan> findAllClientLoans() {
        return clientLoanRepository.findAll();
    }

    @Override
    public ClientLoan findClientLoanByClientIdAndLoanId(Long clientId, Long loanId) {
        return clientLoanRepository.findByClientIdAndLoanId(clientId, loanId);
    }

    @Override
    public boolean existClientLoanByClientLoanAndStatus(Client client, Loan loan, Status status) {
        return clientLoanRepository.existsByClientAndLoanAndStatus(client,loan,status);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findAllTransactionsByAccount(Account account) {
        return transactionRepository.findAllByAccount(account);
    }



    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Loan findLoanByName(String name) {
        return loanRepository.findLoanByName(name);
    }

}
