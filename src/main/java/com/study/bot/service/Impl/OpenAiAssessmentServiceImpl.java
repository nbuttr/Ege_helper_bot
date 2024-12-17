package com.study.bot.service.Impl;

import com.study.bot.dto.assessment.ChatGptRequestDto;
import com.study.bot.dto.assessment.ChatGptResponseDto;
import com.study.bot.service.OpenAiAssessmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OpenAiAssessmentServiceImpl implements OpenAiAssessmentService {

    private final WebClient webClient;

    @Override
    public ChatGptResponseDto solutionAssessment(String questionPhotoUrl, String answerPhotoUrl) {

        List<ChatGptRequestDto.Messages.Content> contents = new ArrayList<>();

        contents.add(ChatGptRequestDto.Messages.Content.builder()
                .type("text")
                .text(("Ты преподователь, на первой картинке задание, на второй решение ученика. Определи правильность решения и дай рекомендации. Не используй в своем ответе формул, твой ответ будет в формате String"))
                .build());

        contents.add(ChatGptRequestDto.Messages.Content.builder()
                .type("image_url")
                .image_url((new ChatGptRequestDto.Messages.Content.ImageUrl(questionPhotoUrl)))
                .build());

        contents.add(ChatGptRequestDto.Messages.Content.builder()
                .type("image_url")
                .image_url((new ChatGptRequestDto.Messages.Content.ImageUrl(answerPhotoUrl)))
                .build());


        List<ChatGptRequestDto.Messages> messages = new ArrayList<>();
        messages.add(ChatGptRequestDto.Messages.builder()
                .role("user")
                .content(contents)
                .build());

        ChatGptRequestDto chatGptRequestDto = ChatGptRequestDto
                .builder()
                .model("gpt-4o-mini")
                .messages(messages)
                .max_tokens(300)
                .build();

        Mono<ChatGptResponseDto> response =
                webClient
                        .post()
                        .bodyValue(chatGptRequestDto)
                        .retrieve()
                        .bodyToMono(ChatGptResponseDto.class);

        return response.block();
    }
}
