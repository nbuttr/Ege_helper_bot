package com.study.bot.state;

import com.study.bot.dto.test.TestDto;
import com.study.bot.dto.userParagraphProgress.UserParagraphProgressDto;
import com.study.bot.state.stage.UserStage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserState {

    private UserStage stage = UserStage.NONE;
    private UUID currentParagraphId;
    private UUID currentSectionId;
    private String tempInput;
    private LocalDateTime lastInteraction;
    private int currIndex;
    private List<TestDto> tests = new ArrayList<>();
    private UserParagraphProgressDto userParagraphProgressDto;
    private String userAnswerImageUrl;
    private String userQuestionImageUrl;

}
