package com.study.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "\"user_paragraph_progress\"")
public class UserParagraphProgress {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "paragraph_id", nullable = false)
    private Paragraph paragraph;

    @Column(name = "test_score", nullable = false)
    private int testScore;

    @Column(name = "max_test_score", nullable = false)
    private int maxTestScore;
}
