import http from '@/service/index.js';

/**
 *  学员管理的后台数据
 */


export const Department = () => {
    return http.get('/manage/dept/getDepartmentAll');
}

export const Users = (param) =>{
    return http.get(`/manage/user/getUsers`, param);
}

export const addUser = (ud) => {
    return http.post('/manage/user/addUser', ud);
}

export const showAll = (id) => {
    return http.get('/manage/user/showAll', id);
}

export const updateUser = (ud) => {
    return http.put('/manage/user/updateUser', ud);
}

export const deleteUser = (id) => {
    return http.delete('/manage/user/deleteUser', id);
}