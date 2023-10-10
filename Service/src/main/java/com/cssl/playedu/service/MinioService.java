package com.cssl.playedu.service;

import com.cssl.playedu.dto.upload.FileChunkInfo;
import io.minio.MinioClient;
import io.minio.StatObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @Author : Tang
 * @CreateDate 2023/9/2 9:06
 */
public interface MinioService {
    String url(String path);
    String getBucketName();

    MinioClient getMinioClient();

    String saveFile(MultipartFile file, String savePath, String contentType) throws Exception;

    List<Integer> getChunkByFileMD5(String objectName, String uploadId);

    Map<String, Object> initMultiPartUpload(FileChunkInfo chunkInfo, String objectName, int chunkNum, String contentType);
    boolean mergeMultipartUpload(String fileHash, String objectName, String uploadId);
    Map<String, Object> getUploadObjectUrl(String objectName);

    StatObjectResponse getObjectInfo( String objectName) throws Exception;
}
