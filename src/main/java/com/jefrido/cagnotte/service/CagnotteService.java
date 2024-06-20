package com.jefrido.cagnotte.service;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.domain.entity.Cagnotte;
import com.jefrido.cagnotte.domain.entity.Client;

import java.math.BigDecimal;

public interface CagnotteService {


    /**
     * Save or update the cagnotte for a client.
     *
     * @param client The client.
     * @param amount The amount of the transaction.
     */
    void saveForTransaction(Client client, BigDecimal amount);

    /**
     * Save the cagnotte for a client.
     *
     * @param client   The client.
     * @param cagnotte The cagnotte to save.
     * @return The {@link Cagnotte}.
     */
    Cagnotte save(Client client, Cagnotte cagnotte);

    /**
     * Retrieve the cagnotte for a client.
     *
     * @param client The client.
     * @return The {@link Cagnotte}.
     */
    Cagnotte getCagnotte(Client client);

    /**
     * Retrieve the cagnotte for a client.
     *
     * @param clientId The client id.
     * @return The {@link CagnotteDto} containing the cagnotte information.
     */
    CagnotteDto getCagnotte(Long clientId);

    /**
     * Check if the cagnotte is available for a client.
     *
     * @param clientId The client id.
     * @return The message indicating if the cagnotte is available.
     */
    String isCagnotteAvailable(Long clientId);
}
