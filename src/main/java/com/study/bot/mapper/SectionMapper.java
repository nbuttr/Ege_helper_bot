package com.study.bot.mapper;

import com.study.bot.dto.section.CreateSectionDto;
import com.study.bot.dto.section.SectionDto;
import com.study.bot.entity.Section;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SectionMapper extends EntityMapper<Section, SectionDto, CreateSectionDto>{

    @Override
    SectionDto toDto(Section entity);

    @Override
    Section createDtoToEntity(CreateSectionDto dto);
}
