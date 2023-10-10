package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.ResourceVideos;

/**
 * @Author : Tang
 * @CreateDate 2023/9/12 17:18
 */
public interface ResourceVideosService extends IService<ResourceVideos> {

    Boolean SaveResourceVideos(ResourceVideos resourceVideos);
}
