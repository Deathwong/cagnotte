package com.jefrido.cagnotte.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.jefrido.cagnotte.domain.entity.Cagnotte}
 */
@Data
public class CagnotteDto implements Serializable {
    Long id;
    BigDecimal totalAmount;
    Integer transactionCount;
}
