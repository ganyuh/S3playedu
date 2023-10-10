package com.cssl.playedu.config;

import com.cssl.playedu.service.AppConfigService;
import com.cssl.playedu.utils.CustomMinioClient;
import io.minio.MinioAsyncClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : Tang
 * @CreateDate 2023/9/11 9:30
 */
@Configuration
public class MinioConfig {

//    @Bean
    public CustomMinioClient getCustomMinioClient(AppConfigService appConfigService) {
        com.cssl.playedu.dto.MinioConfig c = appConfigService.getMinioConfig();
        MinioAsyncClient client =
                CustomMinioClient.builder()
                        .endpoint(c.getEndpoint())
                        .credentials(c.getAccessKey(), c.getSecretKey())
                        .build();
        return new CustomMinioClient(client);
    }

}
