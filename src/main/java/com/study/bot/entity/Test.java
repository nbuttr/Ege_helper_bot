package com.study.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "\"test\"")
@Getter
@Setter
public class Test {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "paragraph_id")
    private Paragraph paragraph;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "answer_1")
    private String answer1;

    @Column(name = "answer_2")
    private String answer2;

    @Column(name = "answer_3")
    private String answer3;

    @Column(name = "answer_4")
    private String answer4;

    @Column(name = "correct_answer")
    private String correctAnswer;

}
