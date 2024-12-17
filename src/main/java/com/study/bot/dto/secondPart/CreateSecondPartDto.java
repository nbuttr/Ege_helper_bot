package com.study.bot.dto.secondPart;

import com.study.bot.entity.Paragraph;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSecondPartDto {

    private String imageUrl;

    private Paragraph paragraph;
}
