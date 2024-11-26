package com.study.bot.service;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import com.study.bot.dto.paragraph.CreateParagraphDto;
import com.study.bot.dto.paragraph.ParagraphDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParagraphService {

    ParagraphDto create(CreateParagraphDto createParagraphDto);

    ParagraphDto update(UUID id, ParagraphDto updatedDto);

    List<ParagraphDto> findAll();

    ParagraphDto findByParagraphName(String paragraphName);

    Optional<ParagraphDto> findById(UUID paragraphId);

    ParagraphDto getById(UUID paragraphId);

    List<ImageToParagraphDto> getImageToParagraphDtos(UUID paragraphId);

}
