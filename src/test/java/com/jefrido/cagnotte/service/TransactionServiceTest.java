package com.jefrido.cagnotte.service;

import com.jefrido.cagnotte.domain.entity.Client;
import com.jefrido.cagnotte.domain.entity.Transaction;
import com.jefrido.cagnotte.exception.error.TransactionNotSaveException;
import com.jefrido.cagnotte.repository.TransactionRepository;
import com.jefrido.cagnotte.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testSave() {
        Client client = TestsUtils.getRandomTestObject(Client.class);
        BigDecimal amount = BigDecimal.valueOf(100);
        Transaction transaction = TestsUtils.getRandomTestObject(Transaction.class);

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.save(client, amount);

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void testSaveFailure() {
        Client client = new Client();
        BigDecimal amount = BigDecimal.valueOf(100);

        when(transactionRepository.save(any(Transaction.class))).thenThrow(new RuntimeException());

        assertThrows(TransactionNotSaveException.class, () -> transactionService.save(client, amount));
    }
}
