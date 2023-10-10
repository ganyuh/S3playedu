package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.ResourceCategories;
import com.cssl.playedu.mapper.ResourceCategoriesMapper;
import com.cssl.playedu.service.ResourceCategoriesService;
import com.cssl.playedu.vo.ResourceCategoriesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceCategoriesServiceImpl extends ServiceImpl<ResourceCategoriesMapper, ResourceCategories> implements ResourceCategoriesService {
    @Autowired
    private ResourceCategoriesMapper resourceCategoriesMapper;


    /**
     *  资源类别表(内容表)
     *  resource_categories
     *
     *  查询全部分类
     */
    @Override
    public List<ResourceCategoriesMap> showAllResourceCategories() {
        return resourceCategoriesMapper.showAllResourceCategories();
    }

    /**
     * 添加方法
     * @param resourceCategories
     * @return
     */
    @Override
    public int addResourceCategories(ResourceCategories resourceCategories) {
        return resourceCategoriesMapper.addResourceCategories(resourceCategories);
    }

    /**
     * 修改方法
     * @param resourceCategories
     * @return
     */
    @Override
    public int updateResourceCategories(ResourceCategories resourceCategories) {
        return resourceCategoriesMapper.updateResourceCategories(resourceCategories);
    }

    /**
     * 删除方法
     * @param id
     * @return
     */
    @Override
    public int deleteResourceCategories(Integer id) {
        return resourceCategoriesMapper.deleteResourceCategories(id);
    }

    /**
     * 删除方法中查询是否存在有其他表格在使用该数据当为0时没有，不为0时表示有，则不进行删除
     * @param id
     * @return
     */
    @Override
    public int getCourseCountByCategoryId(Integer id) {
        return resourceCategoriesMapper.getCourseCountByCategoryId(id);
    }
}
