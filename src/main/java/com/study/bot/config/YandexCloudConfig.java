package com.study.bot.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yws")
@Getter
@Setter
public class YandexCloudConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    @Bean
    public AWSCredentials credentials() {
        return new BasicAWSCredentials(accessKeyId, accessKeySecret);
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("storage.yandexcloud.net", "ru-central1"))
                .build();
    }
}
