import http from '@/service/index.js';

/**
 *  分类管理后台数据
 */
export const ResourceCategories = () => {
    return http.get('/manage/ResourceCategories/showAllResourceCategories');
}

export const Add = (rc) =>{
    return http.post('/manage/ResourceCategories/addResourceCategories',rc);
}

export const Update = (uprc) =>{
    return http.put('/manage/ResourceCategories/updateResourceCategories',uprc);
}

export const Delete = (id) =>{
    return http.delete('/manage/ResourceCategories/deleteResourceCategories',{id:id});
}

export const Getrc = (id) =>{
    return http.get('/manage/ResourceCategories/getResourceCategoriesById',{id:id});
}