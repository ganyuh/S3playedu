<script setup>
import {reactive, ref} from 'vue'
import {useRouter} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";
import {Lock, Promotion, Refresh, Message} from '@element-plus/icons-vue'

import {resetForm} from "@/utils/commons.js";
import imagPath from "@/assets/images/login_left.png";
import {login} from "@/service/modules/backstage/adminUser.js";
import {checkToken} from "@/utils/Base64Utils.js";
import useUserStore from "@/stores/modules/backstage/adminUser/user.js";

/**
 *  登录数据
 */
const loginForm = reactive({
    email: "admin@tang.com",
    password: "123123",
})

/**
 * 表单验证规则
 * */
const rules = reactive({
    email: [
        {required: true, message: "请填写邮箱号", trigger: "blur"},
    ],
    password: [
        {required: true, message: "请填写登录密码", trigger: "blur"},
    ],
})
/**
 * user 状态管理
 * */
const userStore = useUserStore();
const router = useRouter();
/**
 * 登录函数
 * @param formEl
 * @returns {Promise<void>}
 */
const submitLogin = async (formEl) => {
    if (!formEl) return
    await formEl.validate(async (valid) => {
        if (valid) {
            loading.value = true
            try {
                let {data, code, message} = await login(loginForm);
                if (code === 200) {
                    // 解析 token
                    let res = checkToken(data.token);
                    // 保存 token
                    userStore.setToken(data.token);
                    // 跳转页面
                    await router.push("/b")
                    ElNotification({
                        title: '登录成功',
                        message: `欢迎回来！${res.name}`,
                        type: 'success',
                    })
                }
            } catch (e) {
                // ElMessage({
                //     message: "服务器繁忙！",
                //     type: 'error',
                // })
                loading.value = false
            }
        } else {
            ElMessage({
                message: '请输入正确的邮箱和密码！',
                type: 'warning',
            })
        }
    })
}
/**
 * 表单是否处于提交中
 */
const loading = ref(false);
/**
 *  表单元素
 */
const formEl = ref(null);
</script>

<template>
    <div class="login-container">
        <div class="login-box">
            <div class="left">
                <img :src="imagPath"/>
            </div>
            <div class="login-form">
                <div class="title">PlayEdu 培训系统</div>
                <el-form
                        @submit="submitLogin(formEl)"
                        @keyup.enter.native="submitLogin(formEl)"
                        :model="loginForm"
                        status-icon
                        ref="formEl"
                        :rules="rules"
                >
                    <el-form-item prop="email">
                        <el-input v-model="loginForm.email" placeholder="邮箱号"
                                  :prefix-icon="Message"
                        />
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="loginForm.password"
                                  type="password"
                                  placeholder="密码"
                                  show-password
                                  :prefix-icon="Lock"
                        />
                    </el-form-item>
                    <div class="login-btn">
                        <el-button
                                :icon="Promotion"
                                round
                                size="large"
                                type="primary"
                                @click="submitLogin(formEl)"
                                :loading="loading"
                        >登录
                        </el-button>
                        <el-button
                                :icon="Refresh"
                                round
                                size="large"
                                @click="resetForm(formEl)"
                        >重置
                        </el-button>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
</template>

<style>
.login-container {
    width: 100%;
    height: 100%;
    background: url("@/assets/images/login_bg.svg");
    background-size: cover;
    display: flex;
    align-items: center;
    justify-content: center;
}

.login-box {
    position: relative;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: space-around;
    width: 96.5%;
    height: 94%;
    padding: 0 50px;
    background-color: #fffc;
    backdrop-filter: blur(7px);
    border-radius: 10px;
}

.login-box > .login-form > .title {
    text-align: center;
    padding-bottom: 20px;
    font-size: 30px;
    font-weight: 600;
    text-shadow: 4px 3px #c1c1c1;
}

.login-box .left > img {
    width: 800px;
}

.login-box .login-form {
    width: 400px;
    background: #fff;
    border-radius: 20px;
    box-shadow: #0000001a 0 2px 10px 2px;
    padding: 40px 50px;
}

.login-form .login-btn {
    margin-top: 36px;
    display: flex;
    justify-content: space-between;
}

.login-form .login-btn .el-button {
    width: 185px;
}
</style>