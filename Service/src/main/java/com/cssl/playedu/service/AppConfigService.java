package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.AppConfig;
import com.cssl.playedu.dto.MinioConfig;

/**
 * @Author : Tang
 * @CreateDate 2023/9/3 11:46
 */
public interface AppConfigService extends IService<AppConfig> {

    MinioConfig getMinioConfig();

}
