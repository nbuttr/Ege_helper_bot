package com.study.bot.repository;

import com.study.bot.entity.SecondPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SecondPartRepository extends JpaRepository<SecondPart, UUID> {

    List<SecondPart> findByParagraphId(UUID title);
}
