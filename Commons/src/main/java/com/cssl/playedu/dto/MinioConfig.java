package com.cssl.playedu.dto;

import lombok.*;

/**
 * @Author : Tang
 * @CreateDate 2023/9/3 11:50
 * Minio配置
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MinioConfig {
    /**
     * 服务器地址
     */
    private String endpoint;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 访问key
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 文件上传最大分片数量
     */
    private Integer maxPorts;
}
