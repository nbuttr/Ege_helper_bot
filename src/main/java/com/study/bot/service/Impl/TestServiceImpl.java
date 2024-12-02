package com.study.bot.service.Impl;

import com.study.bot.dto.test.CreateTestDto;
import com.study.bot.dto.test.TestDto;
import com.study.bot.mapper.TestMapper;
import com.study.bot.repository.TestRepository;
import com.study.bot.service.TestService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository repository;

    private final TestMapper mapper;

    @Override
    @Transactional
    public TestDto create(CreateTestDto testDto) {
        return mapper.toDto(repository.save(mapper.createDtoToEntity(testDto)));
    }

    @Override
    @Transactional
    public TestDto update(UUID id, TestDto updatedDto) {
        return null;
    }

    @Override
    public List<TestDto> getAllByParagraphId(UUID paragraphId) {
        return repository.findByParagraphId(paragraphId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
