import axios from "axios";
import useUserStore from "../stores/modules/backstage/adminUser/user.js";
import {ElMessage, ElNotification} from "element-plus";
import {useRouter} from "vue-router";

const config = {
    baseURL: "http://localhost:5173/apis",
    // baseURL: "http://localhost:8080/",
    timeout: 5000,
    // 跨域时候允许携带凭证
    withCredentials: true,
};
class http {
    service;

    constructor(config) {
        this.service = axios.create(config);

        /**
         * 配置请求拦截
         * 添加后端验证 Token(JWT)
         */
        this.service.interceptors.request.use(
            config => {
                const userStore = useUserStore();
                if (userStore.token && config.headers && typeof config.headers.set === "function") {
                    config.headers.set("Authorization", `Bearer ${userStore.token}`);
                    // console.log("axios config Header: ", config)
                }
                return config;
            },
            error => Promise.reject(error)
        );

        /**
         * 配置响应拦截
         */
        this.service.interceptors.response.use(response => {
                // console.log("axios service: ", this.service)
                let {data} = response;
                // 判断响应的 code 是否为 601、602
                if (/^601|602/.test(data.code)) {
                    ElNotification({
                        title: "请登录",
                        message: data.message,
                        type: "error",
                    });
                    const router = useRouter();
                    router.push({path: "/login"});
                    return;
                }
                // 判断响应的 code 是否为 50x
                if (/^5\d+/.test(data.code)) {
                    ElNotification({
                        title: "系统错误",
                        message: data.message,
                        type: "error",
                    });
                    // return Promise.reject(data);
                }
                if (/^4\d+/.test(data.code)) {
                    ElNotification({
                        title: "请求失败",
                        message: data.message,
                        type: "error",
                    });
                    // return Promise.reject(data);
                }
                // if (data.code !== 200) {
                //     // ElMessage.error(data.message);
                //     return Promise.reject(data);
                // }
                return data;
            },
            async (error) => {
                const {response} = error;
                // 请求超时 && 网络错误单独判断，没有 response
                if (error.message.indexOf("timeout") !== -1) ElMessage.error("请求超时！请您稍后重试");
                if (error.message.indexOf("Network Error") !== -1) ElMessage.error("网络错误！请您稍后重试");
                // 根据服务器响应的错误状态码，做不同的处理
                // if (response) checkStatus(response.status);
                // 服务器结果都没有返回(可能服务器错误可能客户端断网)，断网处理:可以跳转到断网页面
                // if (!window.navigator.onLine) router.replace("/500");
                return Promise.reject(error);
            }
        );
    }

    /**
     * @description 常用请求方法封装
     */
    get(url, params, _config = {}) {
        return this.service.get(url, {params, ..._config});
    }

    post(url, params, _config = {}) {
        return this.service.post(url, params, _config);
    }

    put(url, params, _config = {}) {
        return this.service.put(url, params, _config);
    }

    delete(url, params, _config = {}) {
        return this.service.delete(url, {params, ..._config});
    }

    download(url, params, _config = {}) {
        return this.service.post(url, params, {..._config, responseType: "blob"});
    }
}

export default new http(config);
