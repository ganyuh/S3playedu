package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.Department;
import com.cssl.playedu.mapper.CourseMapper;
import com.cssl.playedu.mapper.DepartmentMapper;
import com.cssl.playedu.service.DepartmentService;
import com.cssl.playedu.vo.DepartmentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<DepartmentMap> getDepartmentAll() {
        return departmentMapper.getDepartmentAll();
    }
}
