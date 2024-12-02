package com.study.bot.repository;

import com.study.bot.entity.UserParagraphProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserParagraphProgressRepository extends JpaRepository<UserParagraphProgress, UUID> {

}
