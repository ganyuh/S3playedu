package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户学习持续时间统计信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLearnDurationStats implements Serializable {
    /**
     *主键
     */
    private Integer id;
    /**
     *用户id
     */
    private Integer userId;
    /**
     *时长
     */
    private Long duration;
    /**
     *创建时间
     */
    private Date createdDate;

}
