package com.study.bot.repository;

import com.study.bot.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParagraphRepository extends JpaRepository<Paragraph, UUID> {
    List<Paragraph> findAll();

    Paragraph findByParagraphName(String title);

    Optional<Paragraph> findById(UUID id);

    Paragraph getById(UUID id);

}
