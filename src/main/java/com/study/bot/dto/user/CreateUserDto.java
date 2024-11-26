package com.study.bot.dto.user;

import com.study.bot.entity.type.UserRegistrationStatus;
import com.study.bot.entity.type.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private Long chatId;

    private UserRoles userRole;

    private String firstName;

    private String lastName;

    private UserRegistrationStatus registrationStatus;

}
