package com.cssl.playedu.controller.backstage;

import cn.hutool.core.map.MapUtil;
import com.cssl.playedu.constant.BackendConstant;
import com.cssl.playedu.dto.upload.FileChunkInfo;
import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.service.MinioService;
import com.cssl.playedu.service.UploadService;
import com.cssl.playedu.utils.RedisUtil;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author : Tang
 * @CreateDate 2023/9/1 16:50
 */
@RestController
@RequestMapping("/manage/upload")
public class UploadController {

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private MinioService minioService;

    @Autowired
    private UploadService uploadService;

    /**
     * 上传单个文件
     *
     * @param request Http 请求对象
     * @param params  [Integer fileSize], [String videoName], [String ext]
     * @return 上传结果
     */
    @PostMapping("/single")
    public Result<Object> uploadMinio(HttpServletRequest request, @RequestParam Map<String, Object> params) {
        MinioClient mc = minioService.getMinioClient();
        System.out.println("params = " + params);
        Integer fileSize = MapUtil.getInt(params, "fileSize");
        String fileHash = MapUtil.getStr(params, "videoName");
        String ext = MapUtil.getStr(params, "ext");
        if (fileSize == null || fileHash == null || ext == null) {
            throw new BusinessException(ResultCode.REQ_ERROR_PARAM, "参数错误");
        }
        String contentType = BackendConstant.RESOURCE_EXT_2_CONTENT_TYPE.get(ext);
        String path = BackendConstant.RESOURCE_TYPE_2_DIR.get(BackendConstant.RESOURCE_EXT_2_TYPE.get(ext)) + fileHash + "." + ext; // 存储路径
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioService.getBucketName())
                    .object(path)
                    .stream(request.getInputStream(), fileSize, -1)
                    .contentType(contentType)
                    .build();
            //文件名称相同会覆盖
            mc.putObject(objectArgs);
        } catch (Exception e) {
            System.out.println("文件上传失败！");
            e.printStackTrace();
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "文件上传失败！");
        }
        return Result.ok().setMessage("文件上传成功！");
    }

    /**
     * @param fileHash 文件 Hash
     * @description 获取上传文件信息
     */
    @GetMapping("/uploading/{fileHash}")
    public Result<Object> getUploadingFile(@PathVariable String fileHash) {
        if (StringUtils.isBlank(fileHash)) {
            return Result.err("请求参数错误");
        }
        FileChunkInfo fileUploadInfo = (FileChunkInfo) redisUtil.get(fileHash);
        if (fileUploadInfo != null) {
            // 查询上传后的分片数据
            fileUploadInfo.setChunkUploadedList(minioService.getChunkByFileMD5(fileUploadInfo.getSaveName(), fileUploadInfo.getUploadId()));
            return Result.ok().setData(fileUploadInfo);
        }
        return Result.fail().setMessage("请求文件不存在！");
    }

    /**
     * 校验文件是否存在
     *
     * @param hash String
     * @return ResponseResult<Object>
     */
    @GetMapping("/check/{hash}")
    public Result<Object> checkFileUploadedByMd5(@PathVariable String hash) {
        System.out.println("checkFileUploadedBy hash: " + hash);
        if (StringUtils.isEmpty(hash)) {
            System.out.println("文件不存在！");
            return Result.err("请求参数错误！");
        }
        return uploadService.getByFileMD5(hash);
    }

    /**
     * 分片初始化
     *
     * @param fileUploadInfo 文件信息
     * @return ResponseResult<Object>
     */
    @PostMapping("/init")
    public Result<Object> initMultiPartUpload(@RequestBody FileChunkInfo fileUploadInfo) {
        System.out.println("分片初始化 initMultiPartUpload: " + fileUploadInfo);
//        Result<Object> r = uploadService.getByFileMD5(fileUploadInfo.getHashFile());
//        if (r.getCode() == 200) {
//            return Result.fail("该文件已上传完毕！");
//        }
//        if (r.getCode() == ResultCode.UPLOAD_INCOMPLETE) {
//            return Result.fail("该文件正在上传中！").setCode(ResultCode.UPLOAD_INCOMPLETE);
//        }
        String extension = fileUploadInfo.getContentType();
        String type = BackendConstant.RESOURCE_EXT_2_TYPE.get(extension.toLowerCase());
        if (type == null) {
            return Result.fail("该格式文件不支持上传");
        }

        String filename = fileUploadInfo.getFileHash() + "." + extension; // 文件名
        String path = BackendConstant.RESOURCE_TYPE_2_DIR.get(type) + filename; // 存储路径
        fileUploadInfo.setSaveName(path);
//        HashMap<String, String> data = new HashMap<>();
//        data.put("resourceType", type);
//        data.put("fileName", path);

        return Result.ok().setData(uploadService.initMultiPartUpload(fileUploadInfo));
    }

    /**
     * 完成上传
     *
     * @param fileUploadInfo 文件信息
     * @return ResponseResult<Object>
     */
    @PostMapping("/merge")
    public Result<Object> completeMultiPartUpload(@RequestBody FileChunkInfo fileUploadInfo) {
        System.out.println("合并上传任务fileUploadInfo:" + fileUploadInfo);
        //合并文件
        Boolean f = uploadService.mergeMultipartUpload(fileUploadInfo);
        if (!f) {
            return Result.err(ResultCode.FILE_UPLOAD_FAILED, "文件合并失败，请重新上传！");
        }
        return Result.ok().setMessage("文件合并成功！");
    }
}
