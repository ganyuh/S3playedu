package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源类别表(连接15:16两表)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCategory {
    /**
     * 主键
     */
    private Integer cid;
    /**
     * 资源id
     */
    private Integer rid;
}
