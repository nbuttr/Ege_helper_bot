package com.study.bot.state;

import com.study.bot.entity.ImageToParagraph;
import com.study.bot.state.stage.AdminStage;
import com.study.bot.state.stage.UserStage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserState {

    private UserStage stage = UserStage.NONE;
    private UUID currentParagraphId;         // Текущий выбранный параграф
    private String tempInput;                // Временное хранилище для данных
    private LocalDateTime lastInteraction;

}
