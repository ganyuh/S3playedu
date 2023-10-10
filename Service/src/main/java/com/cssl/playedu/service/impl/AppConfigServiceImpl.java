package com.cssl.playedu.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.constant.ConfigKey;
import com.cssl.playedu.domain.AppConfig;
import com.cssl.playedu.dto.MinioConfig;
import com.cssl.playedu.exception.SystemException;
import com.cssl.playedu.mapper.AppConfigMapper;
import com.cssl.playedu.service.AppConfigService;
import com.cssl.playedu.utils.RedisUtil;
import com.cssl.playedu.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : Tang
 * @CreateDate 2023/9/3 11:46
 */
@Service
public class AppConfigServiceImpl extends ServiceImpl<AppConfigMapper, AppConfig> implements AppConfigService {

    @Autowired
    private AppConfigMapper appMapper;

    @Resource
    private RedisUtil redisUtil;


    public Map<String, String> listToMap(List<AppConfig> appConfigList) {
        return appConfigList.stream().collect(Collectors.toMap(AppConfig::getKeyName, AppConfig::getKeyValue));
    }

    /**
     * 判断 Minio 配置是否有效
     */
    public boolean minioConfigValid(Map<String, String> minio) {
        if (MapUtil.isEmpty(minio)) {
            return false;
        }
        return StrUtil.isNotBlank(MapUtil.getStr(minio, ConfigKey.MINIO_ENDPOINT))
                && StrUtil.isNotBlank(MapUtil.getStr(minio, ConfigKey.MINIO_ACCESS_KEY))
                && StrUtil.isNotBlank(MapUtil.getStr(minio, ConfigKey.MINIO_SECRET_KEY))
                && StrUtil.isNotBlank(MapUtil.getStr(minio, ConfigKey.MINIO_BUCKET));
    }

    /**
     * 查询 Minio 配置
     */
    @Override
    public MinioConfig getMinioConfig() {
        MinioConfig minioConfig = null;
        try {
            // 从 Redis 中获取 Minio 配置
            minioConfig = (MinioConfig) redisUtil.get("minioConfig");
        } catch (Exception e) {
            System.out.println("从 Redis 中获取 Minio 配置异常：");
            e.printStackTrace();
        }
        // 判断 Redis 中是否存在 Minio 配置
        if (minioConfig != null) {
            return minioConfig;
        }
        List<AppConfig> appConfigs = appMapper.selectList(new LambdaQueryWrapper<AppConfig>().eq(AppConfig::getGroupName, "Minio"));
        if (appConfigs == null || appConfigs.size() == 0) {
            throw new SystemException(ResultCode.MINIO_NOT_CONFIGURED, "Minio 配置无效");
        }
        Map<String, String> minio = listToMap(appConfigs);
        if (!minioConfigValid(minio)) {
            throw new SystemException(ResultCode.MINIO_NOT_CONFIGURED, "Minio 配置无效");
        }
        minioConfig = new MinioConfig(
                MapUtil.getStr(minio, ConfigKey.MINIO_ENDPOINT),
                MapUtil.getStr(minio, ConfigKey.MINIO_BUCKET),
                MapUtil.getStr(minio, ConfigKey.MINIO_ACCESS_KEY),
                MapUtil.getStr(minio, ConfigKey.MINIO_SECRET_KEY),
                MapUtil.getInt(minio, ConfigKey.MINIO_MAX_PARTS)
        );
        // 将查询到的 MINIO 配置保存到 Redis
        redisUtil.set("minioConfig", minioConfig);
        return minioConfig;
    }

}
