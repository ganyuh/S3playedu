package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.cssl.playedu.domain.Resources;

/**
 * @Author : Tang
 * @CreateDate 2023/9/8 10:00
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resources> {

    Integer insertResource(Resources resources);

}
