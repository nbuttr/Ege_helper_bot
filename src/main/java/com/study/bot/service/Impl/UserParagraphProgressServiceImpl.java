package com.study.bot.service.Impl;

import com.study.bot.dto.userParagraphProgress.CreateUserParagraphProgressDto;
import com.study.bot.dto.userParagraphProgress.UserParagraphProgressDto;
import com.study.bot.entity.Paragraph;
import com.study.bot.entity.UserParagraphProgress;
import com.study.bot.mapper.UserParagraphProgressMapper;
import com.study.bot.repository.UserParagraphProgressRepository;
import com.study.bot.service.UserParagraphProgressService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserParagraphProgressServiceImpl implements UserParagraphProgressService {

    private final UserParagraphProgressRepository repository;

    private final UserParagraphProgressMapper mapper;


    @Override
    @Transactional
    public UserParagraphProgressDto create(CreateUserParagraphProgressDto createUserParagraphProgressDto) {
        return mapper.toDto(repository.save(mapper.createDtoToEntity(createUserParagraphProgressDto)));
    }

    @Override
    @Transactional
    public UserParagraphProgressDto update(UUID id, UserParagraphProgressDto updatedDto) {
        return repository
                .findById(id)
                .map(
                        existingEntity -> {
                            UserParagraphProgress updatedEntity = mapper.partialUpdate(existingEntity, updatedDto);
                            return mapper.toDto(repository.save(updatedEntity));
                        })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<UserParagraphProgressDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
