<script setup>
import {ref} from 'vue'
import logo from "@/assets/logo.png"
import {Avatar, UserFilled} from "@element-plus/icons-vue"

const handleClick = () => {
    // eslint-disable-next-line no-alert
    alert('button click')
}

const currentHome = ref('/b')
</script>
<template>
    <div class="header-nav">
        <div class="logo">
            <router-link :to="currentHome">
                <img :src="logo" alt="logo">
            </router-link>
        </div>
            <slot>
            </slot>
        <div class="current">
            <div class="avatar">
                <el-avatar :icon="UserFilled"
                           src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
            </div>
                <el-dropdown>
                    <div style="outline: none;color: #070707">
                        系统管理员
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>Action 1</el-dropdown-item>
                            <el-dropdown-item>Action 2</el-dropdown-item>
                            <el-dropdown-item>Action 3</el-dropdown-item>
                            <el-dropdown-item>Action 4</el-dropdown-item>
                            <el-dropdown-item>Action 5</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
        </div>
    </div>
</template>

<style scoped>
.header-nav,
.header-nav .current,
.header-nav .current .avatar {
    display: flex;
}

.header-nav {
    height: 100%;
    width: 100%;
    align-items: center;
    justify-content: space-between;
}

.header-nav .logo,
.header-nav .logo img {
    height: 88%;
}

.header-nav .current {
    align-items: center;
    cursor: pointer;
}

.header-nav .current .avatar {
    margin-right: 15px;
}
</style>
