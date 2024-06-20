package com.jefrido.cagnotte.service;

import com.jefrido.cagnotte.domain.entity.Client;

import java.math.BigDecimal;

public interface TransactionService {

    /**
     * Save a transaction for a client.
     *
     * @param client The client.
     * @param amount The amount of the transaction.
     */
    void save(Client client, BigDecimal amount);
}
