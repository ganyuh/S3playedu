package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.constant.BackendConstant;
import com.cssl.playedu.domain.Resources;
import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.mapper.ResourceMapper;
import com.cssl.playedu.service.MinioService;
import com.cssl.playedu.service.ResourcesService;
import com.cssl.playedu.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : Tang
 * @CreateDate 2023/9/8 10:04
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourceMapper, Resources> implements ResourcesService {

    @Autowired
    private MinioService minioService;
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Boolean saveResources(Resources resources) {
        String type = BackendConstant.RESOURCE_EXT_2_TYPE.get(resources.getExtension());
        if (type == null) {
            throw new BusinessException(ResultCode.REQUEST_FAILED, "不支持该文件类型！");
        }
        if (resources.getParentId() == null) {
            resources.setParentId(0);
        }
        resources.setType(type);
        resources.setDisk("minio");
        String path = BackendConstant.RESOURCE_TYPE_2_DIR.get(type) + resources.getFileId() + "." + resources.getExtension(); // 存储路径
        resources.setPath(path);
        String url = minioService.url(path);
        resources.setUrl(url);
        System.out.println("resources = " + resources);
        return resourceMapper.insertResource(resources) > 0;
    }
}
