package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

        SpringApplication.run(HomebankingApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(CardRepository cardRepository,  ClientLoanRepository clientLoanRepository, ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository){
      return args -> {
          System.out.println("app launching");

          // CLientes
          Client client2 = new Client("Luciano","Sapio", "elitaliano76@gmail.com");
          Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
          clientRepository.save(client1);
          clientRepository.save(client2);
          Client client3 = new Client("Elsa", "Patilla", "xXxdetonatorxXx@outlook.com");
          clientRepository.save(client3);


          LocalDate now = LocalDate.now();
          LocalDate tomorrow =  LocalDate.now().plusDays(1);
          LocalDate fiveYears = LocalDate.now().plusYears(5);
            // CUentas
          Account VIN002 = new Account(tomorrow, 6500.00, 2L, client1);
          Account VIN001 = new Account(now,4700.00,1L,client1);
          Account SAP001 = new Account(now, 800.00, 1L, client2 );
          Account SAP002 = new Account(now, 100000.00, 2L, client2);
          Account PAT001 = new Account(tomorrow, 300.00, 1L, client3);

          accountRepository.save(VIN001);
          accountRepository.save(VIN002);
          accountRepository.save(SAP001);
          accountRepository.save(SAP002);
          accountRepository.save(PAT001);



          //Transacciones
          LocalDateTime DT1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 30));
          LocalDateTime DT2 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(6,30));
          LocalDateTime DT3 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12,30));
          LocalDateTime DT4 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(20,30));
          LocalDateTime DT5 = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(8,30));

          Transaction VIN1T001 = new Transaction(DT1, TransactionType.DEBIT, "Fernet for the Homies",-500.00, VIN001);
          Transaction VIN1T002 = new Transaction(DT2, TransactionType.DEBIT, "phone credit", -100.00, VIN001 );
          Transaction VIN1T003 = new Transaction(DT3, TransactionType.CREDIT, "Money for the night", 1000.00, VIN001);
          Transaction VIN2T001 = new Transaction(DT3, TransactionType.DEBIT, "Transfer for the night", -1000.00, VIN002);
          Transaction VIN1T004 = new Transaction(DT4, TransactionType.DEBIT, "Party with the Homies", -700.00, VIN001);

          Transaction SAP1T001 = new Transaction(DT1, TransactionType.CREDIT, "Fernet for the Homies", 500.00, SAP001);
          Transaction SAP1T002 = new Transaction(DT2, TransactionType.CREDIT, "phone credit", 100.00, SAP001) ;
          Transaction SAP1T003 = new Transaction(DT4, TransactionType.CREDIT, "Party with the Homies", 700.00, SAP001);
          Transaction SAP1T004 = new Transaction(DT5, TransactionType.DEBIT, "New products", -1500.00, SAP001);

          transactionRepository.save(VIN1T001);
          transactionRepository.save(VIN1T002);
          transactionRepository.save(VIN1T003);
          transactionRepository.save(VIN1T004);
          transactionRepository.save(VIN2T001);


          transactionRepository.save(SAP1T001);
          transactionRepository.save(SAP1T002);
          transactionRepository.save(SAP1T003);
          transactionRepository.save(SAP1T004);

          //Loans y Payments
          List<Integer> payment1 = List.of(12,24,36,48,60) ;
          List<Integer> payment2 = List.of(6,12,24);
          List<Integer> payment3 = List.of(6,12,24,36);

          Loan mortageLoan = new Loan("Mortgage", 500000.00, payment1);
          Loan personalLoan = new Loan("Personal", 100000.00, payment2);
          Loan autoLoan = new Loan("Auto", 300000.00, payment3);

          loanRepository.save(mortageLoan);
          loanRepository.save(personalLoan);
          loanRepository.save(autoLoan);

          ClientLoan VIN1L001 = new ClientLoan(400000.00, 60, client1, mortageLoan);
          ClientLoan VIN2L002 = new ClientLoan(500000.00, 12, client1, personalLoan);
          ClientLoan SAP1L001 = new ClientLoan(100000.00, 24, client2, personalLoan);
          ClientLoan SAP2L001 = new ClientLoan(200000.00, 36, client2, autoLoan);

          clientLoanRepository.save(VIN1L001);
          clientLoanRepository.save(VIN2L002);
          clientLoanRepository.save(SAP1L001);
          clientLoanRepository.save(SAP2L001);

          // Cards

          Card C1Client1 = new Card(client1 ,CardType.DEBIT, CardColor.GOLD, "938", "Melba-Morel", "3264-2465-5465-5456", now, fiveYears);
          Card C2Client1 = new Card(client1, CardType.DEBIT,CardColor.TITANIUM, "078","Melba-Morel", "6528-4593-7824-4444", now, fiveYears);
          Card C1Client2 = new Card(client2, CardType.CREDIT, CardColor.SILVER, "639", "Luciano-Sapio", "6963-3318-2127-6936", tomorrow, fiveYears);


          cardRepository.save(C1Client1);
          cardRepository.save(C1Client2);
          cardRepository.save(C2Client1);

      };
    }
}
