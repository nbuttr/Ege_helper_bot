package com.study.bot.mapper;

import com.study.bot.dto.userParagraphProgress.CreateUserParagraphProgressDto;
import com.study.bot.dto.userParagraphProgress.UserParagraphProgressDto;
import com.study.bot.entity.UserParagraphProgress;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserParagraphProgressMapper extends EntityMapper<UserParagraphProgress, UserParagraphProgressDto, CreateUserParagraphProgressDto> {

    @Override
    UserParagraphProgressDto toDto(UserParagraphProgress entity);

    @Override
    UserParagraphProgress createDtoToEntity(CreateUserParagraphProgressDto dto);
}
