package com.jefrido.cagnotte.utils.other;

import com.jefrido.cagnotte.utils.constant.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppDateUtilsTest {

    @Test
    void getCurrentDateFormatted() {

        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(AppConstants.EXCHANGE_DATE_FORMAT);
        ZonedDateTime now = ZonedDateTime.now();
        String expectedDate = DATE_FORMATTER.format(now);

        String actualDate = AppDateUtils.getCurrentDateFormatted();

        assertNotNull(actualDate);
        assertEquals(expectedDate, actualDate);
    }
}
