package com.study.bot.service;

import com.study.bot.dto.assessment.ChatGptResponseDto;

public interface OpenAiAssessmentService {
    ChatGptResponseDto solutionAssessment(String questionPhotoUrl, String answerPhotoUrl);
}
