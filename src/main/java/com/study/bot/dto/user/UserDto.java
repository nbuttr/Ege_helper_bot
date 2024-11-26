package com.study.bot.dto.user;

import com.study.bot.entity.type.UserRegistrationStatus;
import com.study.bot.entity.type.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDto {

    private UUID id;

    private Long chatId;

    private UserRoles userRole;

    private UserRegistrationStatus registrationStatus;

    private String firstName;

    private String lastName;

    private UUID journalId;
}
