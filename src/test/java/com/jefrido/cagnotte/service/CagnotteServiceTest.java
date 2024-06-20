package com.jefrido.cagnotte.service;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.domain.entity.Cagnotte;
import com.jefrido.cagnotte.domain.entity.Client;
import com.jefrido.cagnotte.exception.error.CagnotteNotFoundException;
import com.jefrido.cagnotte.exception.error.CagnotteNotSaveException;
import com.jefrido.cagnotte.repository.CagnotteRepository;
import com.jefrido.cagnotte.utils.TestsUtils;
import com.jefrido.cagnotte.utils.constant.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CagnotteServiceTest {

    @Autowired
    private CagnotteService cagnotteService;

    @MockBean
    private CagnotteRepository cagnotteRepository;

    @Test
    public void testSaveForTransaction() {
        Client client = TestsUtils.getRandomTestObject(Client.class);
        BigDecimal amount = BigDecimal.valueOf(100);
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);

        when(cagnotteRepository.existsById(client.getId())).thenReturn(true);
        when(cagnotteRepository.getReferenceById(client.getId())).thenReturn(cagnotte);

        cagnotteService.saveForTransaction(client, amount);

        verify(cagnotteRepository).existsById(client.getId());
        verify(cagnotteRepository).getReferenceById(client.getId());
        verify(cagnotteRepository).save(cagnotte);
    }

    @Test
    public void testSave() {
        Client client = TestsUtils.getRandomTestObject(Client.class);
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);

        when(cagnotteRepository.save(cagnotte)).thenReturn(cagnotte);

        cagnotteService.save(client, cagnotte);

        verify(cagnotteRepository).save(cagnotte);
    }

    @Test
    public void testSaveFailure() {
        Client client = TestsUtils.getRandomTestObject(Client.class);
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);

        when(cagnotteRepository.save(cagnotte)).thenThrow(new RuntimeException());

        assertThrows(CagnotteNotSaveException.class, () -> cagnotteService.save(client, cagnotte));

        verify(cagnotteRepository).save(cagnotte);
    }

    @Test
    public void testGetCagnotte() {
        Client client = TestsUtils.getRandomTestObject(Client.class);
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);

        when(cagnotteRepository.existsById(client.getId())).thenReturn(true);
        when(cagnotteRepository.getReferenceById(client.getId())).thenReturn(cagnotte);

        Cagnotte result = cagnotteService.getCagnotte(client);

        assertNotNull(result);

        verify(cagnotteRepository).existsById(client.getId());
        verify(cagnotteRepository).getReferenceById(client.getId());
    }

    @Test
    public void testGetCagnotteNotFound() {
        Client client = TestsUtils.getRandomTestObject(Client.class);
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);

        when(cagnotteRepository.existsById(client.getId())).thenReturn(false);
        when(cagnotteRepository.save(any())).thenReturn(cagnotte);

        Cagnotte result = cagnotteService.getCagnotte(client);

        assertNotNull(result);

        verify(cagnotteRepository, never()).getReferenceById(client.getId());
        verify(cagnotteRepository).save(any());
    }

    @Test
    public void testGetCagnotteDto() {
        Long clientId = 1L;
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);
        cagnotte.setTransactionCount(10);
        cagnotte.setTotalAmount(BigDecimal.valueOf(100));

        when(cagnotteRepository.findById(clientId)).thenReturn(Optional.of(cagnotte));

        CagnotteDto result = cagnotteService.getCagnotte(clientId);

        assertNotNull(result);
        assertEquals(cagnotte.getTransactionCount(), result.getTransactionCount());
        assertEquals(cagnotte.getTotalAmount(), result.getTotalAmount());
        assertEquals(cagnotte.getId(), result.getId());

        verify(cagnotteRepository, times(2)).findById(clientId);
    }

    @Test
    public void testGetCagnotteDtoNotFound() {
        Long clientId = 1L;

        when(cagnotteRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(CagnotteNotFoundException.class, () -> cagnotteService.getCagnotte(clientId));
        verify(cagnotteRepository, times(1)).findById(clientId);
    }

    @Test
    public void testIsCagnotteAvailable() {
        Long clientId = 1L;
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);
        cagnotte.setTransactionCount(10);
        cagnotte.setTotalAmount(BigDecimal.valueOf(100));

        when(cagnotteRepository.findById(clientId)).thenReturn(Optional.of(cagnotte));

        String result = cagnotteService.isCagnotteAvailable(clientId);

        assertNotNull(result);
        assertEquals(AppConstants.CAGNOTTE_AVAILABLE, result);

        verify(cagnotteRepository).findById(clientId);
    }

    @Test
    public void testIsCagnotteAvailableWrongMinTransactionalCount() {
        Long clientId = 1L;
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);
        cagnotte.setTransactionCount(1);

        when(cagnotteRepository.findById(clientId)).thenReturn(Optional.of(cagnotte));

        String result = cagnotteService.isCagnotteAvailable(clientId);

        assertNotNull(result);
        assertEquals(AppConstants.CAGNOTTE_NOT_AVAILABLE, result);

        verify(cagnotteRepository).findById(clientId);
    }

    @Test
    public void testIsCagnotteAvailableWrongMinTotalAmount() {
        Long clientId = 1L;
        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);
        cagnotte.setTransactionCount(20);
        cagnotte.setTotalAmount(BigDecimal.valueOf(5));

        when(cagnotteRepository.findById(clientId)).thenReturn(Optional.of(cagnotte));

        String result = cagnotteService.isCagnotteAvailable(clientId);

        assertNotNull(result);
        assertEquals(AppConstants.CAGNOTTE_NOT_AVAILABLE, result);

        verify(cagnotteRepository).findById(clientId);
    }

    @Test
    public void testIsCagnotteAvailableNotFound() {
        Long clientId = 1L;

        when(cagnotteRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(CagnotteNotFoundException.class, () -> cagnotteService.isCagnotteAvailable(clientId));

        verify(cagnotteRepository).findById(clientId);
    }
}
