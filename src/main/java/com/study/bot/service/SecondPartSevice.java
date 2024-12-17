package com.study.bot.service;

import com.study.bot.dto.secondPart.CreateSecondPartDto;
import com.study.bot.dto.secondPart.SecondPartDto;

import java.util.List;
import java.util.UUID;

public interface SecondPartSevice {

    SecondPartDto create(CreateSecondPartDto createSecondPartDto);

    List<SecondPartDto> getAllByParagraphId(UUID paragraphId);
}
