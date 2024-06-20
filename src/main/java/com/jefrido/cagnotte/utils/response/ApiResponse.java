package com.jefrido.cagnotte.utils.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> extends AbstractResponse {

    private final T data;

    public ApiResponse(T data) {
        super();
        this.data = data;
    }

    public ApiResponse() {
        super();
        this.data = null;
    }

    public ApiResponse(String message) {
        super(message);
        this.data = null;
    }
}
