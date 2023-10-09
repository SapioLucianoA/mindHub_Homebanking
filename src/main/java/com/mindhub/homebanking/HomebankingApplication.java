package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

        SpringApplication.run(HomebankingApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
      return args -> {
          System.out.println("app launching");

          Client client2 = new Client("Luciano","Sapio", "elitaliano76@gmail.com");
          Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
          clientRepository.save(client1);
          clientRepository.save(client2);
          LocalDate now = LocalDate.now();
          LocalDate tomorrow =  LocalDate.now().plusDays(1);

          Account VIN002 = new Account(tomorrow, 6500L, 2L, client1);
          Account VIN001 = new Account(now,4700L,1L,client1);
          Account SAP001 = new Account(now, 800L, 1L, client2 );

          accountRepository.save(VIN001);
          accountRepository.save(VIN002);
          accountRepository.save(SAP001);
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


      };
    }
}
