package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {

    Optional<ClientLoan> findByClientAndLoan (Client client, Loan loan);
}
