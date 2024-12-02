package com.study.bot.dto.paragraph;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import com.study.bot.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ParagraphDto {

    private UUID id;

    private List<ImageToParagraphDto> imageToParagraphs;

    private String paragraphName;

    private int maxTestMark;

    private int currTestMark;

    private Section section;
}
