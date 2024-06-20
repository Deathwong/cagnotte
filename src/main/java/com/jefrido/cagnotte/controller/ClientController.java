package com.jefrido.cagnotte.controller;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.service.CagnotteService;
import com.jefrido.cagnotte.service.ClientService;
import com.jefrido.cagnotte.utils.constant.AppConstants;
import com.jefrido.cagnotte.utils.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(AppConstants.CLIENT_BASE_PATH)
@Log4j2
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final CagnotteService cagnotteService;

    @PostMapping(AppConstants.ADD_TRANSACTION_PATH)
    public ResponseEntity<ApiResponse<Object>> addTransaction(@PathVariable Long clientId, @RequestParam BigDecimal amount) {

        log.info("Add transaction for client: {}", clientId);

        clientService.addTransaction(clientId, amount);

        return ResponseEntity
            .ok()
            .body(new ApiResponse<>());
    }

    @GetMapping(AppConstants.GET_CAGNOTTE_PATH)
    public ResponseEntity<ApiResponse<CagnotteDto>> getCagnotte(@PathVariable Long clientId) {

        log.info("Get cagnotte for client: {}", clientId);

        CagnotteDto cagnotte = cagnotteService.getCagnotte(clientId);
        ApiResponse<CagnotteDto> response = new ApiResponse<>(cagnotte);

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping(AppConstants.IS_CAGNOTTE_AVAILABLE_PATH)
    public ResponseEntity<ApiResponse<Object>> isCagnotteAvailable(@PathVariable Long clientId) {

        log.info("Check if cagnotte is available for client: {}", clientId);

        String availableMessage = cagnotteService.isCagnotteAvailable(clientId);
        ApiResponse<Object> response = new ApiResponse<>(availableMessage);

        return ResponseEntity
            .ok()
            .body(response);
    }
}
