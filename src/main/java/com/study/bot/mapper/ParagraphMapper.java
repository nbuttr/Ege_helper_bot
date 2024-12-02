package com.study.bot.mapper;

import com.study.bot.dto.paragraph.CreateParagraphDto;
import com.study.bot.dto.paragraph.ParagraphDto;
import com.study.bot.entity.Paragraph;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ParagraphMapper extends EntityMapper<Paragraph, ParagraphDto, CreateParagraphDto> {

    @Override
    ParagraphDto toDto(Paragraph entity);

    @Override
    Paragraph createDtoToEntity(CreateParagraphDto dto);

    @Override
    Paragraph toEntity(ParagraphDto dto);

}
