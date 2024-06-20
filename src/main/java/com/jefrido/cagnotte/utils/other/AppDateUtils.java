package com.jefrido.cagnotte.utils.other;

import com.jefrido.cagnotte.utils.constant.AppConstants;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class AppDateUtils {

    /**
     * Get the current date formatted.
     *
     * @return the current date formatted.
     */
    public static String getCurrentDateFormatted() {
        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(AppConstants.EXCHANGE_DATE_FORMAT);
        ZonedDateTime now = ZonedDateTime.now();
        return DATE_FORMATTER.format(now);
    }
}
