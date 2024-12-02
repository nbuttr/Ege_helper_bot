package com.study.bot.repository;

import com.study.bot.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SectionRepository extends JpaRepository<Section, UUID> {

    List<Section> findAll();

    Section findBySectionName(String title);
}

