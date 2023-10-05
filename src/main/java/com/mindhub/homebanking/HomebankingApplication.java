package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

        SpringApplication.run(HomebankingApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
      return args -> {
          System.out.println("app launching");

          Client client2 = new Client("Luciano","Sapio", "elitaliano76@gmail.com");
          Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
          clientRepository.save(client1);
          clientRepository.save(client2);
          LocalDate now = LocalDate.now();
          LocalDate tomorrow =  LocalDate.now().plusDays(1);

          Account VIN002 = new Account(tomorrow, 7500L, 2L, client1);
          Account VIN001 = new Account(now,5000L,1L,client1);

          accountRepository.save(VIN001);
          accountRepository.save(VIN002);
      };
    }
}
