package com.study.bot.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class WebClientConfig {

    private final OpenAIProperties openAIProperties;

    @Bean
    public WebClient yandexWebClient() {
        return WebClient.builder()
                .baseUrl(openAIProperties.getCustomEndpointUrl())
                .defaultHeader("Authorization", "Bearer " + openAIProperties.getApiKey())
                .build();
    }
}
