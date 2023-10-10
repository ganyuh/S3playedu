<script setup>
import {ref} from "vue";
import {getInfo} from "@/service/modules/backstage/adminUser.js";
import {useRouter} from "vue-router";
import Header from "@components/Header.vue";
import Aside from "@views/backstage/home/Aside.vue";
import UploadBus from "@views/backstage/resource/components/upload/UploadBus.vue";


const router = useRouter();
const adminUser = ref(null);
try {
    // adminUser.value = getInfo();
} catch (e) {
    console.log("getInfo() - error: ", e)
}
/**
 * @description 菜单默认激活项
 */
let menuDefaultActive = ref(router.currentRoute.value.fullPath);
// let a = router.currentRoute.value.fullPath;
// let menuDefaultActive = ref("/b");
const menuSelect = (index) => {
    // console.log("menuSelect: ", index);
    // menuDefaultActive = index;
    // router.push({path: index})
}
</script>

<template>
    <div class="common-layout">
        <el-container class="container">
            <el-header class="header">
                <Header/>
            </el-header>
            <el-container>
                <el-aside class="aside" width="auto">
                    <Aside @menuSelect="menuSelect" :menuDefaultActive="menuDefaultActive"/>
                </el-aside>
                <el-main class="main">
                    <router-view></router-view>
<!--                    <UploadBus/>-->
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<style scoped>
.common-layout,
.container {
    height: 100%;
}

.header {
    --el-header-height: 72px;
    text-align: center;
    line-height: 72px;
}

.aside {
//font-weight: bolder; text-align: center; line-height: 60px;
}

.aside >>> .el-menu-vertical {
    padding: 12px;
    --bg-color: #ff4d4f;
    --el-menu-hover-bg-color: #ff4d4f1a;
    height: 100%;
    width: 200px;
}

.aside >>> .el-menu-vertical .el-sub-menu.is-active > .el-sub-menu__title {
    background-color: var(--el-menu-hover-bg-color);
}

.aside >>> .el-menu-vertical .el-menu-item,
.aside >>> .el-menu-vertical .el-sub-menu__title {
    border-radius: 12px;
    margin: 5px 0;
}

.aside >>> .el-menu-vertical .el-menu-item.is-active {
    background: var(--bg-color);
    color: #f6f6f6;
}

.main {
    background: #f6f6f6;
}
</style>