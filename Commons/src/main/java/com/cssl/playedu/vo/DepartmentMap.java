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
public class DepartmentMap {
    private Integer id;
    private String name;
    private Integer parentId;
    List<ResourceCategoriesMap> ds;
}
