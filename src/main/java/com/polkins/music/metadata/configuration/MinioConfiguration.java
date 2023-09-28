package com.polkins.music.metadata.configuration;

import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.port}")
    private Integer port;

    @Getter
    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean("minioClient")
    public MinioClient minioClient() {
        return  MinioClient.builder()
                .endpoint(endpoint, port, false )
                .credentials(accessKey, secretKey)
                .build();
    }
}
