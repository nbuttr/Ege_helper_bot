package com.study.bot.service.Impl;

import com.study.bot.dto.user.CreateUserDto;
import com.study.bot.dto.user.UserDto;
import com.study.bot.entity.User;
import com.study.bot.mapper.UserMapper;
import com.study.bot.repository.UserRepository;
import com.study.bot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public String create(CreateUserDto createUserDto) {
        userRepository.save(userMapper.createDtoToEntity(createUserDto));
        return "User created successfully";
    }

    @Override
    public UserDto findByChatId(Long chatId) {
        return userMapper.toDto(userRepository.findByChatId(chatId));
    }

    @Override
    public User findEntityByChatId(Long chatId) {
        return userRepository.findByChatId(chatId);
    }
}
