package com.cssl.playedu.domain;
/**
 * 用户学习持续时间记录表
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLearnDurationRecords {

    private long id;
    /**
     * 主键
     */
    private Integer userId;
    /**
     * 用户id
     */
    private Date createdDate;
    /**
     * 创建时间
     */
    private Integer duration;
    /**
     * 已学习时长[微秒]
     */
    private Date startAt;
    /**
     * 开始时间
     */
    private Date endAt;
    /**
     * 结束时间
     */
    private Integer courseId;
    /**
     * 课程id
     */
    private Integer hourId;
    /**
     * 时长id
     */
}
