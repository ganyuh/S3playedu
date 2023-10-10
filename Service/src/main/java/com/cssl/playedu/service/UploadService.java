package com.cssl.playedu.service;

import com.cssl.playedu.dto.upload.FileChunkInfo;
import com.cssl.playedu.vo.Result;

import java.util.Map;

/**
 * @Author : Tang
 * @CreateDate 2023/9/2 11:40
 */
public interface UploadService {
    Result<Object> getByFileMD5(String hash);

    Map<String, Object> initMultiPartUpload(FileChunkInfo fileUploadInfo);

    Boolean mergeMultipartUpload(FileChunkInfo fileUploadInfo);
}
