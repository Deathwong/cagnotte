package com.jefrido.cagnotte.service;

import com.jefrido.cagnotte.domain.entity.Client;
import com.jefrido.cagnotte.exception.error.AmountException;
import com.jefrido.cagnotte.exception.error.ClientNotFoundException;
import com.jefrido.cagnotte.repository.ClientRepository;
import com.jefrido.cagnotte.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private CagnotteService cagnotteService;

    @Test
    public void testAddTransaction() {
        Long clientId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Client client = TestsUtils.getRandomTestObject(Client.class);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.addTransaction(clientId, amount);

        verify(clientRepository).findById(clientId);
        verify(transactionService).save(client, amount);
        verify(cagnotteService).saveForTransaction(client, amount);
    }

    @Test
    public void testAddTransactionWithInvalidAmount() {
        Long clientId = 1L;
        BigDecimal amount = BigDecimal.ZERO;

        assertThrows(AmountException.class, () -> clientService.addTransaction(clientId, amount));

        verify(clientRepository, never()).findById(clientId);
        verifyNoInteractions(transactionService);
        verifyNoInteractions(cagnotteService);
    }

    @Test
    public void testGetClient() {
        Long clientId = 1L;
        Client client = TestsUtils.getRandomTestObject(Client.class);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client result = clientService.getClient(clientId);

        assertNotNull(result);

        verify(clientRepository).findById(clientId);
    }

    @Test
    public void testGetClientNotFound() {
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(clientId));

        verify(clientRepository).findById(clientId);
    }
}
