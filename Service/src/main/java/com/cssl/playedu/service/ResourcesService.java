package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.Resources;

/**
 * @Author : Tang
 * @CreateDate 2023/9/8 10:04
 */
public interface ResourcesService extends IService<Resources> {
    Boolean saveResources(Resources resources);
}
