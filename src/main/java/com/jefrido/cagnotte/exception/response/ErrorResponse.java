package com.jefrido.cagnotte.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jefrido.cagnotte.utils.other.AppDateUtils;
import com.jefrido.cagnotte.utils.response.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonTypeName("error")
public class ErrorResponse extends AbstractResponse {

    private UUID traceId = UUID.randomUUID();
    private String type;
    private String timestamp = AppDateUtils.getCurrentDateFormatted();

    public ErrorResponse(@NonNull Integer code, @NonNull String message, @NonNull Throwable exception) {
        super(code, message);
        this.type = exception.getClass().getSimpleName();
    }
}
