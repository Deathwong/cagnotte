package com.jefrido.cagnotte.exception.response;


import com.jefrido.cagnotte.exception.error.AmountException;
import com.jefrido.cagnotte.exception.error.ApplicationException;
import com.jefrido.cagnotte.exception.error.CagnotteNotFoundException;
import com.jefrido.cagnotte.exception.error.CagnotteNotSaveException;
import com.jefrido.cagnotte.exception.error.ClientNotFoundException;
import com.jefrido.cagnotte.exception.error.TransactionNotSaveException;
import com.jefrido.cagnotte.utils.constant.ExceptionConstantsEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, ApplicationException.class})
    protected ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {

        Integer code = ExceptionConstantsEnum.GENERIC_ERROR.getCode();
        String message = ExceptionConstantsEnum.GENERIC_ERROR.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(code, message, exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

    @ExceptionHandler(value = {CagnotteNotSaveException.class})
    protected ResponseEntity<ErrorResponse> handleCagnotteNotSaveException(Exception exception) {

        Integer code = ExceptionConstantsEnum.JACKPOT_NOT_SAVE_ERROR.getCode();
        String message = ExceptionConstantsEnum.JACKPOT_NOT_SAVE_ERROR.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(code, message, exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

    @ExceptionHandler(value = {TransactionNotSaveException.class})
    protected ResponseEntity<ErrorResponse> handleTransactionNotSaveException(Exception exception) {

        Integer code = ExceptionConstantsEnum.TRANSACTION_NOT_SAVE_ERROR.getCode();
        String message = ExceptionConstantsEnum.TRANSACTION_NOT_SAVE_ERROR.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(code, message, exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

    @ExceptionHandler(value = {CagnotteNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleCagnotteNotFoundException(CagnotteNotFoundException exception) {

        Integer code = ExceptionConstantsEnum.JACKPOT_NOT_FOUND_ERROR.getCode();
        String message = String.format(ExceptionConstantsEnum.JACKPOT_NOT_FOUND_ERROR.getMessage(), exception.getClientId());
        ErrorResponse errorResponse = new ErrorResponse(code, message, exception);

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
    }

    @ExceptionHandler(value = {AmountException.class})
    protected ResponseEntity<ErrorResponse> handleAmountException(Exception exception) {

        Integer code = ExceptionConstantsEnum.AMOUNT_ERROR.getCode();
        String message = ExceptionConstantsEnum.AMOUNT_ERROR.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(code, message, exception);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
    }

    @ExceptionHandler(value = {ClientNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException exception) {

        Integer code = ExceptionConstantsEnum.CLIENT_NOT_FOUND_ERROR.getCode();
        String message = String.format(ExceptionConstantsEnum.CLIENT_NOT_FOUND_ERROR.getMessage(), exception.getClientId());
        ErrorResponse errorResponse = new ErrorResponse(code, message, exception);

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
    }
}
