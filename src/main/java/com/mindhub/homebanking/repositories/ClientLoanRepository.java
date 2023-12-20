package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {

    boolean existsByClientAndLoanAndStatus(Client client, Loan loan, Status status);

    ClientLoan findByClientIdAndLoanId(Long ClientId, Long LoanId);
}
