package com.study.bot.service;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import com.study.bot.dto.paragraph.CreateParagraphDto;
import com.study.bot.dto.paragraph.ParagraphDto;
import com.study.bot.entity.Paragraph;
import com.study.bot.entity.Section;

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

    List<Paragraph> toParagraph(List<ParagraphDto> paragraphDtos);

    Optional<Paragraph> findParagraph(UUID paragraphId);

    List<ParagraphDto> findAllBySectionId(Section section);

    Paragraph getEntityById(UUID paragraphId);

}
