package com.jefrido.cagnotte.utils.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jefrido.cagnotte.utils.constant.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbstractResponse {

    private Integer code;
    private String message;

    public AbstractResponse() {
        this.code = AppConstants.RESPONSE_SUCCESS_CODE;
        this.message = AppConstants.RESPONSE_SUCCESS_MESSAGE;
    }

    public AbstractResponse(String message) {
        this.code = AppConstants.RESPONSE_SUCCESS_CODE;
        this.message = message;
    }
}
