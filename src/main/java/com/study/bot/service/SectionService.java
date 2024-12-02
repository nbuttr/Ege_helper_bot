package com.study.bot.service;

import com.study.bot.dto.section.CreateSectionDto;
import com.study.bot.dto.section.SectionDto;
import com.study.bot.entity.Section;

import java.util.List;
import java.util.UUID;

public interface SectionService {

    SectionDto create(CreateSectionDto sectionDto);

    SectionDto update(UUID id, SectionDto updatedDto);

    SectionDto getById(UUID sectionId);

    List<SectionDto> findAll();

    SectionDto findBySectionName(String sectionName);

    Section getEntityById(UUID id);
}
