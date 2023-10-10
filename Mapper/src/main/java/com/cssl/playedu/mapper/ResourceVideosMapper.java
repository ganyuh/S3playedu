package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cssl.playedu.domain.ResourceVideos;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : Tang
 * @CreateDate 2023/9/12 17:11
 */
@Mapper
public interface ResourceVideosMapper extends BaseMapper<ResourceVideos> {
    Integer insertResourceVideos(ResourceVideos resourceVideos);
}
