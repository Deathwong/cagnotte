package com.jefrido.cagnotte.mapper;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.domain.entity.Cagnotte;
import com.jefrido.cagnotte.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CagnotteMapperTest {

    @Autowired
    private CagnotteMapper cagnotteMapper;

    @Test
    void testToDto() {

        Cagnotte cagnotte = TestsUtils.getRandomTestObject(Cagnotte.class);

        CagnotteDto cagnotteDto = cagnotteMapper.toDto(cagnotte);

        assertNotNull(cagnotteDto);
        assertEquals(cagnotte.getId(), cagnotteDto.getId());
        assertEquals(cagnotte.getTotalAmount(), cagnotteDto.getTotalAmount());
        assertEquals(cagnotte.getTransactionCount(), cagnotteDto.getTransactionCount());
    }
}
