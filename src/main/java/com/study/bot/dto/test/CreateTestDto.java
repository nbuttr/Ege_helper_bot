package com.study.bot.dto.test;

import com.study.bot.entity.Paragraph;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTestDto {

    private String imageUrl;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private String correctAnswer;

    private Paragraph paragraph;
}
