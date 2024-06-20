package com.jefrido.cagnotte.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CagnotteNotFoundException extends RuntimeException {
    private final Long clientId;
}
