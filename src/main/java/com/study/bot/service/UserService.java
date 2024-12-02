package com.study.bot.service;

import com.study.bot.dto.user.CreateUserDto;
import com.study.bot.dto.user.UserDto;
import com.study.bot.entity.User;

public interface UserService {

    String create(CreateUserDto createUserDto);

    UserDto findByChatId(Long chatId);

    User findEntityByChatId(Long chatId);
}
