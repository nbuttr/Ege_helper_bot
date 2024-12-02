package com.study.bot.service.Impl;

import com.study.bot.dto.paragraph.ParagraphDto;
import com.study.bot.dto.section.CreateSectionDto;
import com.study.bot.dto.section.SectionDto;
import com.study.bot.entity.Paragraph;
import com.study.bot.entity.Section;
import com.study.bot.mapper.SectionMapper;
import com.study.bot.repository.SectionRepository;
import com.study.bot.service.ParagraphService;
import com.study.bot.service.SectionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;

    private final SectionMapper mapper;

    private final ParagraphService paragraphService;

    @Override
    @Transactional
    public SectionDto create(CreateSectionDto sectionDto) {
        return mapper.toDto(repository.save(mapper.createDtoToEntity(sectionDto)));
    }

    @Override
    @Transactional
    public SectionDto update(UUID id, SectionDto updatedDto) {
        Section section = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section not found"));

        List<Paragraph> paragraphs = updatedDto.getParagraphs().stream()
                .map(paragraphDto -> {
                    Paragraph paragraph = paragraphService.findParagraph(paragraphDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Paragraph not found"));
                    paragraph.setSection(section);
                    return paragraph;
                })
                .toList();

        section.getParagraphs().clear();
        section.getParagraphs().addAll(paragraphs);

        return mapper.toDto(repository.save(section));
    }

    @Override
    public SectionDto getById(UUID sectionId) {
        return mapper.toDto(
                repository
                        .findById(sectionId)
                        .orElseThrow(
                                NotFoundException::new));
    }

    @Override
    public List<SectionDto> findAll() {
        List<Section> sections = repository.findAll();
        List<SectionDto> sectionDtos = new ArrayList<>();
        for (Section section : sections) {
            sectionDtos.add(mapper.toDto(section));
        }
        return sectionDtos;
    }

    @Override
    public SectionDto findBySectionName(String sectionName) {
        return mapper.toDto(repository.findBySectionName(sectionName));
    }

    @Override
    public Section getEntityById(UUID id) {
        return repository.getById(id);
    }
}
