package com.jefrido.cagnotte.service.impl;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.domain.entity.Cagnotte;
import com.jefrido.cagnotte.domain.entity.Client;
import com.jefrido.cagnotte.exception.error.CagnotteNotFoundException;
import com.jefrido.cagnotte.exception.error.CagnotteNotSaveException;
import com.jefrido.cagnotte.mapper.CagnotteMapper;
import com.jefrido.cagnotte.repository.CagnotteRepository;
import com.jefrido.cagnotte.service.CagnotteService;
import com.jefrido.cagnotte.utils.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
public class CagnotteServiceImpl implements CagnotteService {

    private final CagnotteRepository cagnotteRepository;
    private final CagnotteMapper cagnotteMapper;

    /**
     * Save or update the cagnotte when a transaction is made by a client.
     *
     * @param client The client.
     * @param amount The amount of the transaction.
     */
    @Override
    public void saveForTransaction(Client client, BigDecimal amount) {

        Cagnotte cagnotte = getCagnotte(client);

        final double percentBonus = 0.1;
        BigDecimal bonus = amount.multiply(BigDecimal.valueOf(percentBonus));
        BigDecimal totalAmount = cagnotte.getTotalAmount().add(bonus);
        int transactionCount = cagnotte.getTransactionCount() + 1;

        cagnotte.setTotalAmount(totalAmount);
        cagnotte.setTransactionCount(transactionCount);
        cagnotte.setClient(client);

        save(client, cagnotte);
    }

    /**
     * Save the cagnotte for a client.
     *
     * @param client   The client.
     * @param cagnotte The cagnotte to save.
     * @return The {@link Cagnotte}.
     */
    @Override
    public Cagnotte save(Client client, Cagnotte cagnotte) {
        try {
            return cagnotteRepository.save(cagnotte);
        } catch (Exception e) {
            log.error("Error while saving cagnotte for client: {}", client.getId());
            throw new CagnotteNotSaveException();
        }
    }

    /**
     * Retrieve the cagnotte for a client.
     *
     * @param client The client.
     * @return The {@link Cagnotte}.
     */
    @Override
    public Cagnotte getCagnotte(Client client) {

        if (cagnotteRepository.existsById(client.getId())) {
            return cagnotteRepository.getReferenceById(client.getId());
        }

        // Create a new cagnotte for the client.
        Cagnotte cagnotte = Cagnotte.builder()
            .client(client)
            .totalAmount(BigDecimal.ZERO)
            .transactionCount(0)
            .build();

        return save(client, cagnotte);
    }


    /**
     * Retrieve the cagnotte for a client.
     *
     * @param clientId The client id.
     * @return The {@link CagnotteDto} containing the cagnotte information.
     */
    @Override
    public CagnotteDto getCagnotte(Long clientId) {

        // Check if the cagnotte is available.
        String availableMessage = isCagnotteAvailable(clientId);

        if (availableMessage.equals(AppConstants.CAGNOTTE_NOT_AVAILABLE)) {
            throw new CagnotteNotFoundException(clientId);
        }

        return cagnotteRepository.findById(clientId)
            .map(cagnotteMapper::toDto)
            .orElseThrow(() -> new CagnotteNotFoundException(clientId));
    }

    /**
     * Check if the cagnotte is available for a client.
     *
     * @param clientId The client id.
     * @return The message indicating if the cagnotte is available.
     */
    @Override
    public String isCagnotteAvailable(Long clientId) {

        String availableMessage = AppConstants.CAGNOTTE_AVAILABLE;

        Cagnotte cagnotte = cagnotteRepository.findById(clientId)
            .orElseThrow(() -> new CagnotteNotFoundException(clientId));

        final int minoTransactionalCount = 3;
        final int minTotalAmount = 10;

        if (cagnotte.getTransactionCount() < minoTransactionalCount
            || cagnotte.getTotalAmount().compareTo(BigDecimal.valueOf(minTotalAmount)) < 0) {

            availableMessage = AppConstants.CAGNOTTE_NOT_AVAILABLE;
        }

        return availableMessage;
    }
}
