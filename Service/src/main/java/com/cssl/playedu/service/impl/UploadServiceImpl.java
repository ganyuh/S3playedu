package com.cssl.playedu.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssl.playedu.domain.Resources;
import com.cssl.playedu.dto.upload.FileChunkInfo;
import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.service.MinioService;
import com.cssl.playedu.service.ResourcesService;
import com.cssl.playedu.service.UploadService;
import com.cssl.playedu.utils.RedisUtil;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author : Tang
 * @CreateDate 2023/9/2 11:40
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private ResourcesService resourcesService;

//    @Autowired
    private MinioService minioService;

    /**
     * 按照类型注入 Redis 工具类
     */
    @Resource
    private RedisUtil redisUtil;

    /**
     * 通过 md5 获取已上传的数据（断点续传）
     *
     * @param hash String
     * @return Mono<Map < String, Object>>
     */
    @Override
    public Result getByFileMD5(String hash) {
        System.out.println("查询redis是否存在" + hash);
        // 从redis获取文件名称和id
        FileChunkInfo fileUploadInfo = (FileChunkInfo) redisUtil.get("upload:files:" + hash);
        if (fileUploadInfo != null) {
            // 正在上传，查询上传后的分片数据
            List<Integer> chunkList = minioService.getChunkByFileMD5(fileUploadInfo.getSaveName(), fileUploadInfo.getUploadId());
            fileUploadInfo.setChunkUploadedList(chunkList);
            return Result.ok(fileUploadInfo).setCode(ResultCode.UPLOAD_INCOMPLETE).setMessage("文件上传未完成");

        }

        // 查询数据库是否上传成功
        Resources file = resourcesService.getOne(new QueryWrapper<Resources>().eq("file_id", hash).eq("is_hidden", 0));
        if (file != null) {
            return Result.ok(file).setMessage("上传已完成");
        }

        return Result.ok().setCode(ResultCode.FILE_NOT_UPLOADED).setMessage("文件未上传");
    }

    /**
     * 文件分片上传
     *
     * @param fileUploadInfo
     * @return Mono<Map < String, Object>>
     */
    @Override
    public Map<String, Object> initMultiPartUpload(FileChunkInfo fileUploadInfo) {
        System.out.println("开始初始化<分片上传>任务,initMultiPartUpload fileUploadInfo: " + fileUploadInfo);
        FileChunkInfo redisUploadInfo = (FileChunkInfo) redisUtil.get("upload:files:" + fileUploadInfo.getFileHash());
        if (redisUploadInfo != null) {
            fileUploadInfo = redisUploadInfo;
        }
        System.out.println("开始初始化任务, fileUploadInfo: " + fileUploadInfo);
        String bucketName = minioService.getBucketName();

        Map<String, Object> map = null;
        // 单文件上传
//        if (fileUploadInfo.getChunkNum() == 1) {
//            // 缓存分片上传信息，时效为一天
//            map = minioService.getUploadObjectUrl(fileUploadInfo.getSaveName());
//        }
//        // 分片上传
//        else {
            String contentType = "application/octet-stream";
            map = minioService.initMultiPartUpload(fileUploadInfo, fileUploadInfo.getSaveName(), fileUploadInfo.getChunkNum(), contentType);
//        }
        String uploadId = MapUtil.getStr(map, "uploadId");
        fileUploadInfo.setUploadId(uploadId);
        // 缓存分片上传信息，时效为一天
        redisUtil.set("upload:files:" + fileUploadInfo.getFileHash(), fileUploadInfo, 1, TimeUnit.DAYS);
        return map;
    }

    /**
     * 文件合并
     *
     * @param
     * @return String
     */
    @Override
    public Boolean mergeMultipartUpload(FileChunkInfo fileUploadInfo) {
        System.out.println("开始合并任务,mergeMultipartUpload fileUploadInfo: " + fileUploadInfo);
        FileChunkInfo redisFileUploadInfo = (FileChunkInfo) redisUtil.get("upload:files:" + fileUploadInfo.getFileHash());
        if (redisFileUploadInfo == null) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "文件合并失败，请重新上传！");
        }
        return minioService.mergeMultipartUpload(redisFileUploadInfo.getFileHash(), redisFileUploadInfo.getSaveName(), redisFileUploadInfo.getUploadId());
    }
}
