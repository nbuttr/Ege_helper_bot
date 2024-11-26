package com.study.bot.dto.ImageToParagraphDto;

import com.study.bot.dto.paragraph.ParagraphDto;
import com.study.bot.entity.type.ImageTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ImageToParagraphDto {

    private UUID id;

    private ParagraphDto paragraph;

    private String url;

    private ImageTypes imageType;

    private int ordinalNumber;

}
