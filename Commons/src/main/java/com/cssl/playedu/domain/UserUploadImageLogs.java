package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户上传图像日志表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUploadImageLogs implements Serializable {
    /**
     *主键
     */
    private Integer id;
    /**
     *用户id;
     */
    private Integer userId;
    /**
     *图片类型
     */
    private String typed;
    /**
     *上传场景
     */
    private String scene;
    /**
     *驱动
     */
    private String driver;
    /**
     *相对路劲
     */
    private String path;
    /**
     *访问地址
     */
    private String url;
    /**
     *大小单位字节
     */
    private Long size;
    /**
     *文件名
     */
    private String name;
    /**
     *创建时间
     */
    private Date createdAt;
}
