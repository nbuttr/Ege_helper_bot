package com.study.bot.mapper;

import com.study.bot.dto.user.CreateUserDto;
import com.study.bot.dto.user.UserDto;
import com.study.bot.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends EntityMapper<User, UserDto, CreateUserDto> {

    @Override
    UserDto toDto(User entity);

    @Override
    User toEntity(UserDto dto);

    @Override
    @Mapping(source = "firstName", target = "userFirstName")
    @Mapping(source = "lastName", target = "userLastName")
    @Mapping(source = "registrationStatus", target = "userRegistrationStatus")
    User createDtoToEntity(CreateUserDto dto);
}
