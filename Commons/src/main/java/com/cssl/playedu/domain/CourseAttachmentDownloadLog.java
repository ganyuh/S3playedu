package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程附件下载表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAttachmentDownloadLog {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 学员ID
     */
    private Integer userId;
    /**
     * 课程ID
     */
    private Integer courseId;
    /**
     * 课程标题
     */
    private String title;
    /**
     * 课程附件id
     */
    private Integer courseAttachmentId;
    /**
     * 资源id
     */
    private Integer rid;
    /**
     * 下载ip
     */
    private String ip;
    /**
     * 创建时间
     */
    private Date createAt;
}
