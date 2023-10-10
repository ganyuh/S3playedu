package com.cssl.playedu.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.cssl.playedu.dto.MinioConfig;
import com.cssl.playedu.dto.upload.FileChunkInfo;
import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.service.AppConfigService;
import com.cssl.playedu.service.MinioService;
import com.cssl.playedu.utils.CustomMinioClient;
import com.cssl.playedu.utils.RedisUtil;
import com.cssl.playedu.vo.ResultCode;
import com.google.common.collect.HashMultimap;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author : Tang
 * @CreateDate 2023/9/2 11:38
 */
@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private AppConfigService appConfigService;

    @Resource
    private RedisUtil redisUtil;

//    @Autowired
    private CustomMinioClient customMinioClient;

    @Override
    public String url(String path) {
        MinioConfig c = appConfigService.getMinioConfig();
        return c.getEndpoint()
                + (c.getEndpoint().endsWith("/") ? "" : "/")
                + c.getBucketName()
                + "/"
                + path;
    }

    @Override
    public String getBucketName() {
        return appConfigService.getMinioConfig().getBucketName();
    }

    @Override
    public MinioClient getMinioClient() {
        MinioConfig minio = appConfigService.getMinioConfig();
        return MinioClient.builder()
                .endpoint(minio.getEndpoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .build();
    }

    /**
     * 保存文
     *
     * @param file        MultipartFile
     * @param savePath    保存路径
     * @param contentType 文件类型
     * @return 路径
     * @throws Exception
     */
    @Override
    public String saveFile(MultipartFile file, String savePath, String contentType) throws Exception {
        PutObjectArgs objectArgs = PutObjectArgs.builder()
                .bucket(getBucketName())
                .object(savePath)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(contentType)
                .build();
        //文件名称相同会覆盖
        getMinioClient().putObject(objectArgs);
        return "/manage/resource/preview/" + savePath;
    }

    /**
     * 通过 sha256 获取上传中的分片信息
     *
     * @param objectName 文件全路径名称
     * @param uploadId   返回的uploadId
     * @return List<Integer>
     */
    @Override
    public List<Integer> getChunkByFileMD5(String objectName, String uploadId) {
        try {
            // 查询上传后的分片数据
            ListPartsResponse partResult = customMinioClient.listMultipart(getBucketName(), null, objectName, 1000, 0, uploadId, null, null);
            System.out.println("getChunkByFileMD5 分片数量：" + partResult.result().partList());
            return partResult.result().partList().stream().map(Part::partNumber).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("获取分片失败，异常：");
            e.printStackTrace();
            throw new BusinessException(ResultCode.FILE_RESUMPTION_FAILED, "获取分片失败");
        }
    }

    /**
     * 单文件签名上传
     *
     * @param objectName 文件全路径名称
     * @return /
     */
    public Map<String, Object> getUploadObjectUrl(String objectName) {
        System.out.println("单文件上传：" + objectName);
        try {
            Map<String, Object> resMap = new HashMap();
            List<String> partList = new ArrayList<>();
            String url = customMinioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(getBucketName())
                            .object(objectName)
                            .expiry(1, TimeUnit.DAYS)
                            .build());
            partList.add(url);
            resMap.put("uploadId", "SingleFileUpload");
            resMap.put("urlList", partList);
            return resMap;
        } catch (Exception e) {
            System.out.println("获取上传地址失败，异常：");
            e.printStackTrace();
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "获取上传地址失败");
        }
    }

    /**
     * 初始化分片上传
     *
     * @param chunkInfo   分片上传信息
     * @param objectName  文件全路径名称
     * @param chunkNum    分片数量
     * @param contentType 类型，如果类型使用默认流会导致无法预览
     * @return Mono<Map < String, Object>>
     */
    public Map<String, Object> initMultiPartUpload(FileChunkInfo chunkInfo, String objectName, int chunkNum, String contentType) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            String bucketName = getBucketName();
            if (CharSequenceUtil.isBlank(contentType)) {
                contentType = "application/octet-stream";
            }
            HashMultimap<String, String> headers = HashMultimap.create();

            headers.put("Content-Type", contentType);

//            CustomMinioClient customMinioClient = getCustomMinioClient();
            //获取uploadId
            String uploadId = null;
            if (StrUtil.isBlank(chunkInfo.getUploadId())) {
                uploadId = customMinioClient.uploadId(bucketName, null, objectName, headers, null);
            } else {
                uploadId = chunkInfo.getUploadId();
            }

            resMap.put("uploadId", uploadId);

            chunkInfo.setUploadId(uploadId);
            chunkInfo.setChunkNum(chunkNum);

            List<String> partList = new ArrayList<>();

            Map<String, String> reqParams = new HashMap<>();
            reqParams.put("uploadId", uploadId);
            for (int i = 1; i <= chunkNum; i++) {
                reqParams.put("partNumber", String.valueOf(i));
                String uploadUrl = customMinioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.PUT)
                                .bucket(bucketName)
                                .object(objectName)
                                .expiry(1, TimeUnit.DAYS)
                                .extraQueryParams(reqParams)
                                .build());
                partList.add(uploadUrl);
            }
            System.out.println("tip message: 文件初始化<分片上传>、成功");
            resMap.put("urlList", partList);
            return resMap;
        } catch (Exception e) {
            System.out.println("初始化分片上传失败, 异常错误: ");
            e.printStackTrace();
            // 返回 文件上传失败
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "文件上传失败");
        }
    }

    /**
     * 分片上传完后合并
     *
     * @param objectName 文件全路径名称
     * @param uploadId   返回的uploadId
     * @return boolean
     */
    public boolean mergeMultipartUpload(String fileHash, String objectName, String uploadId) {
        try {
//            CustomMinioClient customMinioClient = getCustomMinioClient();
            MinioConfig minioConfig = appConfigService.getMinioConfig();
            System.out.println("MinioConfig: " + minioConfig);
            String bucketName = getBucketName();
            System.out.println("文件合并，开始：");
            System.out.println("FileName: " + objectName);
            System.out.println("uploadId: " + uploadId);
            //目前仅做了最大100分片
            Part[] parts = new Part[minioConfig.getMaxPorts()];
            // 查询上传后的分片数据
            ListPartsResponse partResult = customMinioClient.listMultipart(bucketName, null, objectName, minioConfig.getMaxPorts(), 0, uploadId, null, null);
            int partNumber = 1;
            for (Part part : partResult.result().partList()) {
                parts[partNumber - 1] = new Part(partNumber, part.etag());
                partNumber++;
            }
            // 合并分片
            customMinioClient.mergeMultipartUpload(bucketName, null, objectName, uploadId, parts, null, null);
            // 删除redis 缓存
            redisUtil.del("upload:files:" + fileHash);
            System.out.println("文件合并结束，FileHash：" + fileHash);
        } catch (Exception e) {
            System.out.println("文件合并，失败：");
            e.printStackTrace();
            //TODO 删除redis的数据
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "文件上传失败");
        }
        return true;
    }

    /**
     * 获取文件信息
     *
     * @param objectName  文件名称
     * @throws Exception <a href="https://docs.minio.io/cn/java-client-api-reference.html#statObject">原文档地址</a>
     */
    @Override
    public StatObjectResponse getObjectInfo(String objectName) throws Exception {
        return getMinioClient().statObject(StatObjectArgs.builder()
                .bucket(getBucketName())
                .object(objectName)
                .build());
    }
}
