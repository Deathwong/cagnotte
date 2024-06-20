package com.jefrido.cagnotte.mapper;

import com.jefrido.cagnotte.domain.dto.CagnotteDto;
import com.jefrido.cagnotte.domain.entity.Cagnotte;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CagnotteMapper {
    CagnotteDto toDto(Cagnotte cagnotte);
}
