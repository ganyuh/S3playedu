<script setup>

import TangIcon from "@components/TangIcon.vue";
import {defineEmits, ref} from "vue";

/**
 * 定义当前组件属性
 */
defineProps({
    menuDefaultActive: {
        type: String,
        default: '/b'
    }
})
const emits = defineEmits(['menuSelect'])
/**
 *  左边菜单是否收起
 */
const isCollapse = ref(false)
</script>

<template>
    <el-menu
            :default-active="menuDefaultActive"
            class="el-menu-vertical"
            :collapse="isCollapse"
            :router="true"
            @select="emits('menuSelect', $event)"
    >
        <el-menu-item index="/b">
            <TangIcon class-name="icon-home"/>
            <span>首页概览</span>
        </el-menu-item>
        <el-menu-item index="/b/resource-category">
            <TangIcon class-name="icon-category"/>
            <span>分类管理</span>
        </el-menu-item>
        <el-sub-menu index="/b/resource-manage">
            <template #title>
                <TangIcon class-name="icon-file"/>
                <span>资源管理</span>
            </template>
            <el-menu-item index="/b/resource-manage/video">视频</el-menu-item>
            <el-menu-item index="/b/resource-manage/image">图片</el-menu-item>
            <el-menu-item index="/b/resource-manage/annex">课件</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4">
            <template #title>
                <TangIcon class-name="icon-study"/>
                <span>课程中心</span>
            </template>
            <el-menu-item index="/b/classes">线上课</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
            <template #title>
                <TangIcon class-name="icon-user"/>
                <span>学员管理</span>
            </template>
            <el-menu-item index="/b/student">学员</el-menu-item>
            <el-menu-item index="/b/dept">部门</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="6">
            <template #title>
                <TangIcon class-name="icon-setting"/>
                <span>系统设置</span>
            </template>
            <el-menu-item index="6-1">管理人员</el-menu-item>
            <el-menu-item index="6-2">管理日志</el-menu-item>
            <el-menu-item index="6-3">系统配置（...）</el-menu-item>
        </el-sub-menu>
    </el-menu>
</template>

<style scoped>

</style>