package com.jefrido.cagnotte.service.impl;

import com.jefrido.cagnotte.domain.entity.Client;
import com.jefrido.cagnotte.domain.entity.Transaction;
import com.jefrido.cagnotte.exception.error.TransactionNotSaveException;
import com.jefrido.cagnotte.repository.TransactionRepository;
import com.jefrido.cagnotte.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * Save a transaction for a client.
     *
     * @param client The client.
     * @param amount The amount of the transaction.
     */
    @Override
    public void save(Client client, BigDecimal amount) {

        Transaction transaction = Transaction.builder()
            .client(client)
            .amount(amount)
            .build();

        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Error while saving transaction for client: {}", client.getId());
            throw new TransactionNotSaveException();
        }
    }
}
