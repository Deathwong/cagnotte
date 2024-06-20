package com.jefrido.cagnotte.service.impl;

import com.jefrido.cagnotte.domain.entity.Client;
import com.jefrido.cagnotte.exception.error.AmountException;
import com.jefrido.cagnotte.exception.error.ClientNotFoundException;
import com.jefrido.cagnotte.repository.ClientRepository;
import com.jefrido.cagnotte.service.CagnotteService;
import com.jefrido.cagnotte.service.ClientService;
import com.jefrido.cagnotte.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final TransactionService transactionService;
    private final CagnotteService cagnotteService;

    /**
     * Add transaction for a client.
     *
     * @param clientId The client id.
     * @param amount   The amount of the transaction.
     */
    @Override
    public void addTransaction(Long clientId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountException();
        }

        Client client = getClient(clientId);
        transactionService.save(client, amount);
        cagnotteService.saveForTransaction(client, amount);
    }

    /**
     * Retrieve the client by id.
     *
     * @param clientId The client id.
     * @return The {@link Client}.
     */
    public Client getClient(Long clientId) {
        return clientRepository
            .findById(clientId)
            .orElseThrow(() -> new ClientNotFoundException(clientId));
    }
}
