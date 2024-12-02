package com.study.bot.dto.userParagraphProgress;

import com.study.bot.entity.Paragraph;
import com.study.bot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserParagraphProgressDto {

    private User user;

    private Paragraph paragraph;

    private int testScore;

    private int maxTestScore;
}
