package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户部门表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDepartment implements Serializable {
    /**
     *用户id
     */
    private Integer userId;
    /**
     *部门id
     */
    private Integer depId;

    /**
     * user对应部门
     */
    private List<Department> department;
}
