package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {
}
