package com.study.bot.service;

import com.study.bot.dto.userParagraphProgress.CreateUserParagraphProgressDto;
import com.study.bot.dto.userParagraphProgress.UserParagraphProgressDto;

import java.util.List;
import java.util.UUID;

public interface UserParagraphProgressService {

    UserParagraphProgressDto create(CreateUserParagraphProgressDto createUserParagraphProgressDto);

    UserParagraphProgressDto update(UUID id, UserParagraphProgressDto updatedDto);

    List<UserParagraphProgressDto> findAll();
}
