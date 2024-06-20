package com.jefrido.cagnotte.utils.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionConstantsEnum {

    // OTHERS
    GENERIC_ERROR("Internal technical error detected", 3000),

    // JACKPOT
    JACKPOT_NOT_FOUND_ERROR("Jackpot not found for client id = %s", 4004),
    JACKPOT_NOT_SAVE_ERROR("Jackpot cannot be save", 5000),

    // TRANSACTION
    AMOUNT_ERROR("Amount must be greater than 0", 4000),
    TRANSACTION_NOT_SAVE_ERROR("Transaction cannot be save", 5000),

    // CLIENT
    CLIENT_NOT_FOUND_ERROR("Client not found for id = %s", 4004);

    private final String message;
    private final Integer code;
}
