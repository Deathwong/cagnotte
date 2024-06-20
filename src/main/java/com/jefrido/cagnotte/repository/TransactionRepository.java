package com.jefrido.cagnotte.repository;

import com.jefrido.cagnotte.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
