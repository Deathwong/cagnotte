package com.jefrido.cagnotte.service;

import com.jefrido.cagnotte.domain.entity.Client;

import java.math.BigDecimal;

public interface ClientService {

    /**
     * Add transaction for a client.
     *
     * @param clientId The client id.
     * @param amount   The amount of the transaction.
     */
    void addTransaction(Long clientId, BigDecimal amount);

    /**
     * Retrieve the client by id.
     *
     * @param clientId The client id.
     * @return The {@link Client}.
     */
    Client getClient(Long clientId);
}
