package com.study.bot.repository;

import com.study.bot.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, UUID> {

    List<Test> findByParagraphId(UUID title);
}
