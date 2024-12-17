package com.study.bot.service.Impl;

import com.study.bot.dto.secondPart.CreateSecondPartDto;
import com.study.bot.dto.secondPart.SecondPartDto;
import com.study.bot.mapper.SecondPartMapper;
import com.study.bot.repository.SecondPartRepository;
import com.study.bot.service.SecondPartSevice;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecondPartServiceImpl implements SecondPartSevice {

    SecondPartRepository repository;

    SecondPartMapper mapper;

    @Override
    @Transactional
    public SecondPartDto create(CreateSecondPartDto createSecondPartDto) {
        return mapper.toDto(repository.save(mapper.createDtoToEntity(createSecondPartDto)));
    }

    @Override
    public List<SecondPartDto> getAllByParagraphId(UUID paragraphId) {
        return repository.findByParagraphId(paragraphId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
