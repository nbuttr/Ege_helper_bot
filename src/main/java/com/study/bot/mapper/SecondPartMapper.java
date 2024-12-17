package com.study.bot.mapper;

import com.study.bot.dto.secondPart.CreateSecondPartDto;
import com.study.bot.dto.secondPart.SecondPartDto;
import com.study.bot.entity.SecondPart;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SecondPartMapper extends EntityMapper<SecondPart, SecondPartDto, CreateSecondPartDto> {

    @Override
    SecondPart toEntity(SecondPartDto dto);

    @Override
    SecondPartDto toDto(SecondPart entity);

    @Override
    SecondPart createDtoToEntity(CreateSecondPartDto dto);
}
