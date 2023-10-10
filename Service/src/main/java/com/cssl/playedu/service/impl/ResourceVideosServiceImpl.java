package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.ResourceVideos;
import com.cssl.playedu.mapper.ResourceVideosMapper;
import com.cssl.playedu.service.MinioService;
import com.cssl.playedu.service.ResourceVideosService;
import com.cssl.playedu.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author : Tang
 * @CreateDate 2023/9/12 17:20
 */
@Service
public class ResourceVideosServiceImpl extends ServiceImpl<ResourceVideosMapper, ResourceVideos> implements ResourceVideosService {

    @Autowired
    private MinioService minioService;
    @Autowired
    private ResourceVideosMapper resourceMapper;

    @Autowired
    private ResourcesService resourcesService;

    /**
     * 保存视频信息
     * 【将视频文件信息和视频封面文件信息保存至 resources 表】
     * 【resource_videos 保存视频和封面的 ID 以及视频时长】
     * @param resourceVideos ResourceVideos 对象
     * @return Boolean
     */
    @Transactional
    @Override
    public Boolean SaveResourceVideos(ResourceVideos resourceVideos) {
        // 将视频信息保存至 resources 表
        Boolean saveRes = resourcesService.saveResources(resourceVideos.getVideo());
        // 获取视频信息在数据可中的 ID
        Integer rid = resourceVideos.getVideo().getId();
        //  设置视频信息 ID
        resourceVideos.setRid(rid);
        resourceVideos.getFrontCover().setParentId(rid);
        // 将视频信封面息保存至 resources 表
        Boolean savefc = resourcesService.saveResources(resourceVideos.getFrontCover());
        // 设置封面信息 ID
        resourceVideos.setPoster(resourceVideos.getFrontCover().getId());
        // 保存视频信息
        Boolean saveVideo = resourceMapper.insertResourceVideos(resourceVideos) > 0;
        return savefc && saveRes && saveVideo;
    }
}
