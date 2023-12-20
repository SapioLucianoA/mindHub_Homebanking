package com.mindhub.homebanking.RepositoriesTest;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mindhub.homebanking.models.ClientRole.ADMIN;
import static com.mindhub.homebanking.models.ClientRole.CLIENT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {



    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUpClient(){
        clientRepository.saveAllAndFlush(List.of(
                new Client("Guille", "null", "guille@guille.com", "123", ADMIN),
                new Client("Martin", "Client", "Martin@Martin.com", "123", CLIENT),
                new Client("Gimena", "Alcaraz", "gime@gime.com", "123", CLIENT)
        ));
    }

    @BeforeEach
    void setUpLoans(){
        loanRepository.saveAllAndFlush(List.of(
                new Loan("circus", 50000.00, new ArrayList<Integer>(2)),
                new Loan("Magazine", 30000.00, new ArrayList<Integer>(3)),
                new Loan("Caiman", 50000.00, new ArrayList<Integer>(4))
        ));
    }
    @BeforeEach
    void setUpAccount(){
        accountRepository.saveAllAndFlush(List.of(
                new Account(LocalDate.now(), 300000.00,"VIN-340", clientRepository.findByEmail("guille@guille.com")),
                new Account(LocalDate.now(), 999999.00, "VIN-231", clientRepository.findByEmail("Martin@Martin.com"))
        ));
    }

    @BeforeEach
    void setUpTransaction(){
        transactionRepository.saveAllAndFlush(List.of(
                new Transaction(LocalDateTime.now(), TransactionType.CREDIT, "asd", 10000.00,accountRepository.findByNumber("VIN-231")),
                new Transaction(LocalDateTime.now(), TransactionType.DEBIT, "asd", -10000.00, accountRepository.findByNumber("VIN-340"))
        ));
    }




    @Test
    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));


    }



    @Test
    public void existLoanName(){
        List<Loan> loans = loanRepository.findAll();
        assertTrue(loans.stream().anyMatch(loan -> loan.getName().equals("circus")));
    }

    @Test
    public  void existClientEmail(){
        boolean client = clientRepository.existsByEmail("Martin@Martin.com");
        assertThat(client, is(true));
    }
    @Test
    public void lastNameNull(){
        Client client = clientRepository.findByEmail("guille@guille.com");
        assertThat(client.getLastName(), is("null"));

    }

    @Test
    public void accountexistByNumber(){
        boolean account = accountRepository.existsByNumber("VIN-231");
        assertThat(account, is(true));
    }

    @Test
            public  void ExistNumber(){
    List<Account> accounts = accountRepository.findAll();
    assertTrue(accounts.stream().anyMatch(account -> account.getNumber().equals("VIN-231")));
    }
    @Test
    public void transactionnegative(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertTrue(transactions.stream().anyMatch(transaction -> transaction.getAmount() < 0));
    }
    @Test
    public void transactionEqualsTo(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertTrue(transactions.stream().anyMatch(transaction -> transaction.getAmount().equals(10000.00)));
    }
}
