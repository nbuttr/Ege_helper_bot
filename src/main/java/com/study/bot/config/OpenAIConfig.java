package com.study.bot.config;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.ProxyOptions;
import com.azure.core.http.okhttp.OkHttpAsyncHttpClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@AllArgsConstructor
public class OpenAIConfig {

    private final OpenAIProperties properties;

    @Bean
    public OpenAIClient openAIClient() {

        OkHttpAsyncHttpClientBuilder okHttpAsyncHttpClientBuilder = new OkHttpAsyncHttpClientBuilder();
        if (Boolean.TRUE.equals(properties.getProxy().getEnabled()))
            okHttpAsyncHttpClientBuilder.proxy(
                    new ProxyOptions(
                            ProxyOptions.Type.HTTP,
                            new InetSocketAddress(
                                    properties.getProxy().getHostname(), properties.getProxy().getPort()))
                            .setCredentials(
                                    properties.getProxy().getUsername(), properties.getProxy().getPassword()));

        OpenAIClientBuilder openAIbuilder =
                new OpenAIClientBuilder().credential(new AzureKeyCredential(properties.getApiKey()));

        if (properties.getCustomEndpointUrl() != null && !properties.getCustomEndpointUrl().isEmpty()) {
            openAIbuilder.endpoint(properties.getCustomEndpointUrl());
        }

        openAIbuilder.httpClient(okHttpAsyncHttpClientBuilder.build());

        return openAIbuilder.buildClient();
    }
}
