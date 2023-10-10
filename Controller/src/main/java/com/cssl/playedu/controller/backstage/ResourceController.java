package com.cssl.playedu.controller.backstage;

import cn.hutool.core.util.StrUtil;
import com.cssl.playedu.domain.AdminUsers;
import com.cssl.playedu.domain.ResourceVideos;
import com.cssl.playedu.domain.Resources;
import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.service.MinioService;
import com.cssl.playedu.service.ResourceVideosService;
import com.cssl.playedu.service.ResourcesService;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : Tang
 * @CreateDate 2023/9/1 20:05
 */
@RestController
@RequestMapping("/manage/resource")
public class ResourceController {

//    @Autowired
    private MinioService minioService;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private ResourceVideosService resourceVideosService;


    private static final List<String> OPTIONS = new ArrayList<>(2);

    static {
        OPTIONS.add("preview");
        OPTIONS.add("download");
    }

    @GetMapping({"get/{option}/**"})
    public void download(@PathVariable String option, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (!OPTIONS.contains(option)) {
            throw new BusinessException(ResultCode.ERROR_NOT_FOUND, "参数错误");
        }
        MinioClient mc = minioService.getMinioClient();
//        response.setHeader("Content-Type", "application/octet-stream");
//        String fileName = "";
        String url = request.getRequestURI();
        Pattern pattern = Pattern.compile("^/manage/resource/(" + StrUtil.join("|", OPTIONS) + ")/");
        Matcher matcher = pattern.matcher(url);
        String path = matcher.replaceAll("");
        String fileName = Pattern.compile("^.*/").matcher(url).replaceAll("");
        System.out.println("path:" + path);
        System.out.println("fileName:" + fileName);
        if ("download".equals(option)) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        }
        try {
            StatObjectResponse object = mc.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioService.getBucketName())
                            .object(path).build()
            );
            response.setContentType(object.contentType());
            InputStream in = mc.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioService.getBucketName())
                            .object(path)
                            .build()
            );
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            System.out.println("Exception Name: " + e.getClass().getName());
            System.out.println("下载时出现错误: ");
            e.printStackTrace();
            throw new BusinessException(ResultCode.FILE_DOWNLOAD_FAILED, "文件不存在！");
        }
    }


    /**
     * 将上传到Minion中的文件信息保存至数据库
     *
     * @param resources Resources 对象
     */
    @PostMapping("/save")
    public Result<Object> addResource(@RequestBody Resources resources, HttpSession session) {
        AdminUsers adminUsers = (AdminUsers) session.getAttribute("adminUser");
        resources.setAdminId(adminUsers.getId());
        Result<Object> r = Result.err("资源添加失败！");
        try {
            boolean save = resourcesService.saveResources(resources);
            if (save) {
                return r.setCode(ResultCode.SUCCESS).setMessage("资源添加成功！");
            }
        } catch (Exception e) {
            System.out.println("资源添加失败：");
            e.printStackTrace();
        }
        return r;
    }

    /**
     * 保存视频信息
     */
    @PostMapping("/saveVideo")
    public Result<Object> saveCover(@RequestBody ResourceVideos resource, HttpSession session) {
        Result<Object> r = Result.fail("视频信息保存失败！");
        AdminUsers adminUsers = (AdminUsers) session.getAttribute("adminUser");
        resource.getVideo().setAdminId(adminUsers.getId());
        resource.getFrontCover().setAdminId(adminUsers.getId());
        try {
            Boolean saved = resourceVideosService.SaveResourceVideos(resource);
            r = saved ? Result.ok("视频信息保存成功！") : r;
        } catch (Exception e) {
            System.out.println("资源添加失败：");
            e.printStackTrace();
        }
        return r;
    }
}
