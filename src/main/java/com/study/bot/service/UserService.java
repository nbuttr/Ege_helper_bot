package com.study.bot.service;

import com.study.bot.dto.user.CreateUserDto;
import com.study.bot.dto.user.UserDto;

public interface UserService {

    String create(CreateUserDto createUserDto);

    UserDto findByChatId(Long chatId);
}
