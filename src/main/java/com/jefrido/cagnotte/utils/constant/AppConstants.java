package com.jefrido.cagnotte.utils.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {

    // OTHERS
    public static final String RESPONSE_SUCCESS_MESSAGE = "Success";
    public static final Integer RESPONSE_SUCCESS_CODE = 1000;
    public static final String EXCHANGE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // CONTROLLER
    public static final String CLIENT_BASE_PATH = "/api/v1/client";
    public static final String ADD_TRANSACTION_PATH = "/{clientId}/transactions";
    public static final String GET_CAGNOTTE_PATH = "/{clientId}/cagnotte";
    public static final String IS_CAGNOTTE_AVAILABLE_PATH = "/{clientId}/cagnotte/available";

    // SERVICES
    public static final String CAGNOTTE_NOT_AVAILABLE = "jackpot not available";
    public static final String CAGNOTTE_AVAILABLE = "jackpot is available";
}
