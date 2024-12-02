package com.study.bot.mapper;

import com.study.bot.dto.test.CreateTestDto;
import com.study.bot.dto.test.TestDto;
import com.study.bot.entity.Test;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TestMapper extends EntityMapper<Test, TestDto, CreateTestDto>{

    @Override
    TestDto toDto(Test entity);

    @Override
    Test createDtoToEntity(CreateTestDto dto);

}
