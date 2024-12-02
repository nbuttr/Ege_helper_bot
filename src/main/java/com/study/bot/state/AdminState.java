package com.study.bot.state;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import com.study.bot.state.stage.AdminStage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminState {

    private AdminStage stage = AdminStage.NONE;
    private String currentParagraphName;
    private UUID currentParagraphId;
    private UUID currentSectionId;
    private String currentSectionName;
    private List<ImageToParagraphDto> imagesToParagraph = new ArrayList<>();
    private String url;
    private String testUrl;
    private int currentOrdinalNumber;
    private String testAnswer1;
    private String testAnswer2;
    private String testAnswer3;
    private String testAnswer4;
    private String correctAnswer;

}
