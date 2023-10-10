import http from '@/service/index.js';

/**
 *  课程中心的后台数据
 */

export const ResourceCategories = () => {
  return http.get('/manage/ResourceCategories/showAllResourceCategories');
}

export const Department = () => {
  return http.get('/manage/dept/getDepartmentAll');
}

export const Course = (cou) =>{
  return http.get('/manage/course/showAllCourse',cou);
}