package com.study.bot.repository;

import com.study.bot.entity.Paragraph;
import com.study.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByChatId(Long chatId);

    Optional<User> findById(UUID id);

    User getById(UUID id);
}
