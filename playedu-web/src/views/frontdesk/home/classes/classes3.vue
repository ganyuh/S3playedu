<script setup>
    import Header from "@/components/Header.vue";
    import Chapter from "@views/frontdesk/home/classes/Chapter.vue";
    import {ref} from "vue";

    const videoList = ref([
        {
            name: '示例视频-Microsoft 365 Copilot in PowerPoint',
            url: "/b"
        }, {
            name: '示例视频-Microsoft 365 Copilot in PowerPoint',
            url: "/b"
        }, {
            name: '示例视频-Microsoft 365 Copilot in PowerPoint',
            url: "/b"
        },
    ])
</script>
<template>
    <div id="root">
        <Header>
            <div class="el-menu-top">
                <el-menu
                        default-active="1"
                        mode="horizontal"
                >
                    <router-link to="/fd">
                        <el-menu-item index="3">首页</el-menu-item>
                    </router-link>
                    <el-menu-item index="2">最近学习</el-menu-item>
                </el-menu>
            </div>
        </Header>
        <div>
            <div class="up_box">
                <div>
                    <img src="src/assets/images/003.png" class="up_box_img">
                    <div class="context">产品精力入门指南
                        <div>
                            必修课
                        </div>
                    </div>
                    <div class="demo-progress">
                        <el-progress type="circle" :percentage="0"/>
                    </div>
                </div>
                <div>
                    入职产品经理的最有效路径、数据产品经理、项目管理、原型设计等与产品经理相关的学习内容。
                </div>
            </div>
        </div>
        <div>
            <div class="directory">
                <span>课程目录</span>
            </div>
        </div>
        <div class="fa_context">
            <div class="content">
                <Chapter chapterTitle="第一章" :videoList="videoList"/>
            </div>
        </div>
    </div>
</template>
<style>
    #root {
        display: grid;
        justify-content: center;
        justify-items: center;
    }

    .el-menu-top {
        margin-right: auto;
        margin-left: 31px;
    }

    .up_box {
        width: 1200px;
        height: auto;
        background: #ffffff;
        box-shadow: 0 2px 8px 4px #0000000a;
        border-radius: 12px;
        margin-top: 30px;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
        padding: 24px;
    }

    .up_box_img {
        height: 90px;
        border-radius: 10px;
    }

    .demo-progress .el-progress--circle {
        margin-right: 15px;
    }

    .directory {
        width: 64px;
        height: 40px;
        font-size: 16px;
        font-weight: 500;
        color: #ff4d4f;
        line-height: 40px;
        margin-right: 10px;

    }
    .fa_context{
        width: 100%;
        height: 200px;
        background: #ffffff;
        box-shadow: 0 2px 8px 4px #0000000a;
        border-radius: 12px;
        margin-top: 24px;
        box-sizing: border-box;
        padding: 24px;
    }
    .content {
        width: 100%;
        height: 56px;
        background: #ffffff;
        border-radius: 6px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-sizing: border-box;
        padding: 0 24px;
        cursor: pointer;
        margin-top: 30px;
    }
</style>