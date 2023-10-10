package com.cssl.playedu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户课程时间记录表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseHourRecords implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     *用户id
     */
    private Integer userId;
    /**
     *课程id
     */
    private Integer courseId;
    /**
     *时间id
     */
    private Integer hourId;
    /**
     *总用时
     */
    private Integer totalDuration;
    /**
     *已完成时长
     */
    private Integer finishedDuration;
    /**
     *实际观看时长
     */
    private Integer realDuration;
    /**
     *是否看完【1：是，：否】
     */
    @TableField("is_finished")
    private Boolean finished;
    /**
     *看完时间
     */
    private Date finishedAt;
    /**
     *创建时间
     */
    private Date createdAt;
    /**
     *修改时间
     */
    private Date updatedAt;
}
