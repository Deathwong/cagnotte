package com.jefrido.cagnotte.controller;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.service.CagnotteService;
import com.jefrido.cagnotte.service.ClientService;
import com.jefrido.cagnotte.utils.TestsUtils;
import com.jefrido.cagnotte.utils.constant.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private CagnotteService cagnotteService;

    @Test
    public void testAddTransaction() throws Exception {
        long clientId = 1;
        BigDecimal amount = BigDecimal.valueOf(100);

        doNothing().when(clientService).addTransaction(clientId, amount);

        mockMvc.perform(post(AppConstants.CLIENT_BASE_PATH + "/" + clientId + "/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("amount", amount.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(AppConstants.RESPONSE_SUCCESS_MESSAGE));

        verify(clientService).addTransaction(clientId, amount);
    }

    @Test
    public void testGetCagnotte() throws Exception {
        long clientId = 1;
        CagnotteDto cagnotte = TestsUtils.getRandomTestObject(CagnotteDto.class);

        when(cagnotteService.getCagnotte(clientId)).thenReturn(cagnotte);

        mockMvc.perform(get(AppConstants.CLIENT_BASE_PATH + "/" + clientId + "/cagnotte")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data").exists());

        verify(cagnotteService).getCagnotte(clientId);
    }

    @Test
    public void testIsCagnotteAvailable() throws Exception {
        Long clientId = 1L;

        when(cagnotteService.isCagnotteAvailable(clientId)).thenReturn(AppConstants.CAGNOTTE_AVAILABLE);

        mockMvc.perform(get(AppConstants.CLIENT_BASE_PATH + "/" + clientId + "/cagnotte/available")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(AppConstants.CAGNOTTE_AVAILABLE));

        verify(cagnotteService).isCagnotteAvailable(clientId);
    }
}
