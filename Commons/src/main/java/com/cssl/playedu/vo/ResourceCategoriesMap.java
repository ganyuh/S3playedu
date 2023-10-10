package com.cssl.playedu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCategoriesMap {
    private Integer rc1id;
    private String rc1name;
    private Integer rc1parentId;
    List<ResourceCategoriesMap> rcs;
}
