package com.study.bot.dto.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TestDto {

    private UUID id;

    private String imageUrl;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private String correctAnswer;

}
