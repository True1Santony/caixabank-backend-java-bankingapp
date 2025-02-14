package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.Transaction;
import com.hackathon.finservice.Entities.TransactionStatus;
import com.hackathon.finservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByTransactionStatus_Status(TransactionStatus.Status status);

    List<Transaction> findTop5BySenderAccountOrderByTimestampDesc(Account senderAccount);

    List<Transaction> findByAccount_User(User user);

}
