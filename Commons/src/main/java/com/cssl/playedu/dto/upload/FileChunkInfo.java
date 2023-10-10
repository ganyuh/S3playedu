package com.cssl.playedu.dto.upload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author : Tang
 * @CreateDate 2023/9/7 15:50
 */
@Data
public class FileChunkInfo implements Serializable {
    private Long serialVersionUID = 1L;

    /**
     * 分配数量
     */
    @NotNull(message = "分片数量不能为空")
    private Integer chunkNum;

    /**
     * MinIO uploadID
     */
    @NotBlank(message = "uploadId 不能为空")
    private String uploadId;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 已上传的分片索引+1
     */
    private List<Integer> chunkUploadedList;
    /**
     * 原文件名称
     */
    private String originalName;
    /**
     * 保存在 Minio 中的文件名称
     */
    private String saveName;
    /**
    /**
     * 文件 hash
     */
    private String fileHash;
    /**
     * 文件的类型
     */
    private String contentType;
}