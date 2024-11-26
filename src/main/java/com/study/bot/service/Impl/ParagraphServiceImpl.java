package com.study.bot.service.Impl;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import com.study.bot.dto.paragraph.CreateParagraphDto;
import com.study.bot.dto.paragraph.ParagraphDto;
import com.study.bot.entity.Paragraph;
import com.study.bot.mapper.ParagraphMapper;
import com.study.bot.repository.ParagraphRepository;
import com.study.bot.service.ParagraphService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParagraphServiceImpl implements ParagraphService {

    private final ParagraphRepository repository;

    private final ParagraphMapper mapper;

    @Override
    @Transactional
    public ParagraphDto create(CreateParagraphDto createParagraphDto) {
        return mapper.toDto(repository.save(mapper.createDtoToEntity(createParagraphDto)));
    }

    @Override
    @Transactional
    public ParagraphDto update(UUID id, ParagraphDto updatedDto) {
        return repository
                .findById(id)
                .map(
                        existingEntity -> {
                            Paragraph updatedEntity = mapper.partialUpdate(existingEntity, updatedDto);
                            return mapper.toDto(repository.save(updatedEntity));
                        })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<ParagraphDto> findAll() {
        List<Paragraph> paragraphs = repository.findAll();
        List<ParagraphDto> paragraphDtos = new ArrayList<>();
        for (Paragraph paragraph : paragraphs) {
            paragraphDtos.add(mapper.toDto(paragraph));
        }
        return paragraphDtos;
    }

    @Override
    public ParagraphDto findByParagraphName(String paragraphName) {
        return mapper.toDto(repository.findByParagraphName(paragraphName));
    }

    @Override
    public Optional<ParagraphDto> findById(UUID paragraphId) {
        return repository.findById(paragraphId).map(mapper::toDto);
    }

    @Override
    public ParagraphDto getById(UUID paragraphId) {
        return mapper.toDto(
                repository
                        .findById(paragraphId)
                        .orElseThrow(
                                NotFoundException::new));
    }

    @Override
    public List<ImageToParagraphDto> getImageToParagraphDtos(UUID paragraphId) {
        return getById(paragraphId).getImageToParagraphs();
    }
}
