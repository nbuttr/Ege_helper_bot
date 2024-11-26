package com.study.bot.state;

import com.study.bot.entity.ImageToParagraph;
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
    private List<ImageToParagraph> imagesToParagraph = new ArrayList<>();
    private String url;
    private int currentOrdinalNumber;

}
