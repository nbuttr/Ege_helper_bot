package com.study.bot.dto.userParagraphProgress;

import com.study.bot.entity.Paragraph;
import com.study.bot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserParagraphProgressDto {

    private UUID id;

    private User user;

    private Paragraph paragraph;

    private int testScore;

    private int maxTestScore;
}
