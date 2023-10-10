package com.cssl.playedu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户课程记录表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseRecords implements Serializable {
    /**
     *主键
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
     *课程数量
     */
    private Integer hourCount;
    /**
     *已完成课程时数
     */
    private Integer finishedCount;
    /**
     *进度
     */
    private Integer progress;
    /**
     *看完【1：是，0：否】
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
