package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(UUID targetAccountNumber);

    List<Account> findAllByAccountTypeAndBalanceGreaterThan(String invest, double balance);
}
