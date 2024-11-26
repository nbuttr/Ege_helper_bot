package com.study.bot.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.study.bot.config.YandexCloudConfig;
import com.study.bot.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
@AllArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3;

    private final YandexCloudConfig cloudConfig;

    @Override
    public String uploadFileToS3(String url) {
        File file = new File(url);
        String uuid = UUID.randomUUID() +".jpg";
        s3.putObject(cloudConfig.getBucketName(), uuid, file);
        return s3.getUrl(cloudConfig.getBucketName(), uuid).toString();
    }
}
