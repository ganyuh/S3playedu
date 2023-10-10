package com.cssl.playedu.dto.upload;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : Tang
 * @CreateDate 2023/9/2 11:00
 */
@Data
public class UploadFileInfo implements Serializable {
    private Long serialVersionUID = 1L;
    /**
     * 原文件名称
     */
    private String originalName;
    /**
     * 文件扩展名
     */
    private String extension;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 在前端计算的文件 hash
     * 文件保存在 Minio 中的名称
     */
    private String saveName;
    /**
     * 文件的类型
     */
    private String contentType;
    /**
     * 文件保存在 Minion 中的路径
     */
    private String savePath;

    /**
     * 访问的url
     */
    private String url;

}
