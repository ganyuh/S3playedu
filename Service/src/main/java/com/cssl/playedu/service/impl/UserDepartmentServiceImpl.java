package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.UserDepartment;
import com.cssl.playedu.mapper.UserDepartmentMapper;
import com.cssl.playedu.service.UserDepartmentService;
import org.springframework.stereotype.Service;

@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements UserDepartmentService {
}
