import http from '@/service';

/**
 *  管理员登录
 * @param adminUser ({email, password})
 * @returns {*}
 */
export const login = (adminUser) => {
    return http.post('/manage/admin/login', adminUser);
}

export const getInfo = () => {
    return http.get('/manage/admin/info');
}