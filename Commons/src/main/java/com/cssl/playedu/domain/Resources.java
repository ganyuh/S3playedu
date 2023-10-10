package com.cssl.playedu.domain;

/**
 * 资源表
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resources extends Object {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 管理员id
     */
    private Integer adminId;
    /**
     * 类型
     */
    private String type;
    /**
     * 资源名原来的名称
     */
    private String name;
    /**
     * 文件类型
     */
    private String extension;
    /**
     * 大小[字节]
     */
    private long size;
    /**
     * 存储磁盘
     */
    private String disk;
    /**
     * 文件 Hash，保存在 Minio 中的 名称
     */
    private String fileId;
    /**
     * 相对地址
     */
    private String path;
    /**
     * url地址
     */
    private String url;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 所属素材
     */
    private Integer parentId;
    /**
     * 是否隐藏[0:否1:是]
     */
    @TableField("is_hidden")
    private Boolean hidden;
}
