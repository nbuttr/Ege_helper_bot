package com.study.bot.dto.section;

import com.study.bot.dto.paragraph.ParagraphDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SectionDto {

    private UUID id;

    private String sectionName;

    private List<ParagraphDto> paragraphs;
}
