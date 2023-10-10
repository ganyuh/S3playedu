package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.Department;
import com.cssl.playedu.vo.DepartmentMap;
import com.cssl.playedu.vo.ResourceCategoriesMap;

import java.util.List;

public interface DepartmentService extends IService<Department> {

    List<DepartmentMap> getDepartmentAll();
}
