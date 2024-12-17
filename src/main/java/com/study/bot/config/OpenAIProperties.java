package com.study.bot.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.openai")
public class OpenAIProperties {

    private String apiKey;
    private String customEndpointUrl;
    private Proxy proxy = new Proxy();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Proxy {
        private String hostname;
        private Integer port;
        private String username;
        private String password;
        private Boolean enabled;
    }
}