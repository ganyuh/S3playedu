package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cssl.playedu.domain.ResourceCategories;
import com.cssl.playedu.vo.ResourceCategoriesMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceCategoriesMapper extends BaseMapper<ResourceCategories> {
    /**
     *  资源类别表(内容表)
     *  resource_categories
     *
     *  查询全部分类
     */
    List<ResourceCategoriesMap> showAllResourceCategories();

    /**
     * 添加方法
     * @param resourceCategories
     * @return
     */
    int addResourceCategories(ResourceCategories resourceCategories);

    /**
     * 修改方法
     * @param resourceCategories
     * @return
     */
    int updateResourceCategories(ResourceCategories resourceCategories);

    /**
     * 删除方法
     */
    int deleteResourceCategories(Integer id);

    /**
     * 删除方法中查询是否存在有其他表格在使用该数据当为0时没有，不为0时表示有，则不进行删除
     * @param id
     * @return
     */
    int getCourseCountByCategoryId(Integer id);
}
