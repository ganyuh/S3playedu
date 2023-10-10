package com.cssl.playedu.domain;
/**
 * 应用程序配置表
 */


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {
    /**
     * 主键
     */
    private long id;
    /**
     * 分组
     */
    private String groupName;
    /**
     * 名称
     */
    private String name;
    /**
     * 升序
     */
    private Integer sort;
    /**
     * +
     * 字段类型
     */
    private String fieldType;
    /**
     * 键
     */
    private String keyName;
    /**
     * 值
     */
    private String keyValue;
    /**
     * 可选值
     */
    private String optionValue;
    /**
     * 是否私密信息
     */
    @TableField("is_private")
    private Boolean isPrivate;
    /**
     * 帮助信息
     */
    private String help;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 是否隐藏
     */
    @TableField("is_hidden")
    private Boolean hidden;
}
