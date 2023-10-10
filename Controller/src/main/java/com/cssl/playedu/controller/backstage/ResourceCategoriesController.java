package com.cssl.playedu.controller.backstage;

import com.cssl.playedu.domain.ResourceCategories;
import com.cssl.playedu.service.ResourceCategoriesService;
import com.cssl.playedu.vo.ResourceCategoriesMap;
import com.cssl.playedu.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/ResourceCategories")
public class ResourceCategoriesController {
    @Autowired
    private ResourceCategoriesService resourceCategoriesservice;

    @GetMapping("/showAllResourceCategories")
    public Result<List<ResourceCategoriesMap>> showAllResourceCategories() {
        List<ResourceCategoriesMap> rcs = resourceCategoriesservice.showAllResourceCategories();
        return Result.ok(rcs);
    }

    @PostMapping("/addResourceCategories")
    public Result<Object> addResourceCategories(@RequestBody ResourceCategories resourceCategories){
        int  result = resourceCategoriesservice.addResourceCategories(resourceCategories);
        return Result.ok(result);
    }

    @PutMapping("/updateResourceCategories")
    public Result<Object> updateResourceCategories(@RequestBody ResourceCategories resourceCategories){
        int result = resourceCategoriesservice.updateResourceCategories(resourceCategories);
        return Result.ok(result);
    }

    @GetMapping("/getResourceCategoriesById")
    public Result<Object> getCourseCountByCategoryId(@RequestParam(required = false) Integer id){
        int rc = resourceCategoriesservice.getCourseCountByCategoryId(id);
        System.out.println(rc);
        return Result.ok(rc);
    }

    @DeleteMapping("deleteResourceCategories")
    public Result<Object> deleteResourceCategories(int id){
        int result = resourceCategoriesservice.deleteResourceCategories(id);
        return Result.ok(result);
    }
}
