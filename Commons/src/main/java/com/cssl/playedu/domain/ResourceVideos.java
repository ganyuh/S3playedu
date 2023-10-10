package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 视屏资源表表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVideos {
    /**
     * 主键
     */
    private Integer rid;
    /**
     * 封面 (resource ID)
     */
    private Integer poster;
    /**
     * 视屏时长[s]
     */
    private Integer duration;
    /**
     * 创建时间
     */
    private Date createdId;

    /**
     * 视频文信息
     */
    private Resources video;
    /**
     * 视频文封面信息
     */
    private Resources frontCover;
}
