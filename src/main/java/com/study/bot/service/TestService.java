package com.study.bot.service;

import com.study.bot.dto.section.SectionDto;
import com.study.bot.dto.test.CreateTestDto;
import com.study.bot.dto.test.TestDto;

import java.util.List;
import java.util.UUID;

public interface TestService {

    TestDto create(CreateTestDto testDto);

    TestDto update(UUID id, TestDto updatedDto);

    List<TestDto> getAllByParagraphId(UUID paragraphId);
}
