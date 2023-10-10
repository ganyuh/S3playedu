<script setup>
    import logo from "@/assets/logo.png"
    import {ref} from "vue";
    import Header from "@/components/Header.vue";
    import {
        ArrowDown,
        Check,
        CircleCheck,
        CirclePlus,
        CirclePlusFilled,
        Plus,
    } from '@element-plus/icons-vue'

    const activeName = ref('first')

    const handleClick = (tab, event) => {
        console.log(tab, event)
    }

</script>

<template>
<div id="root">
    <Header>
        <div class="el-menu-top">
            <el-menu
                    default-active="1"
                    mode="horizontal"
            >
                <router-link to="/fd"><el-menu-item index="3">首页</el-menu-item></router-link>

                <el-menu-item index="2">最近学习</el-menu-item>
            </el-menu>
        </div>
    </Header>
    <div>
        <div class="father_top">
        <div class="top_content" style="display: inline-block">
            <div>
                <a>
                    <img class="top_picture" src="../../../assets/images/frontdesk/front01.png">
                </a>
                <span class="typeface">课程进度</span>

            </div>
            <div>
                <div class="count">
                    <div class="count1">必修课:已完成课程</div>
                    <div class="count1">选修课:未完成课程</div>
                </div>
            </div>
        </div>
        <div class="top_content" style="display: inline-block">

                <div>
                <a>
                    <img class="top_picture" src="../../../assets/images/frontdesk/front02.png"><span class="typeface">学习时长</span>
                </a>
            </div>
            <div>
                <div class="count">
                    <div class="count1">今日:</div>
                    <div class="count1">累计:</div>
                </div>
            </div>

        </div>
        </div>
        <div class="options">
            <el-menu
                    class="el-menu-demo"
                    mode="horizontal"
            >
            </el-menu>
            <div class="h-6">
                <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
                    <el-tab-pane label="全部" name="first"><div>
                        <router-link to="/fd/classes">
                        <div class="course" style="display: inline-block">
                            <div>
                                <div style="width: 120px; height: 90px;">
                                    <img src="../../../assets/images/frontdesk/front04.png" width="120" height="90" style="height: 90px; border-radius: 10px;">
                                </div>
                                <div>
                                    <div>产品经理入门指南</div>
                                    <div>必修课</div>
                                </div>
                            </div>
                            <div>
                                <div role="progressbar" aria-valuenow="0" style="width: 270px;">
                                    <el-progress :percentage="0" />
                                </div>
                                <span>未学习</span>
                            </div>
                        </div>
                        </router-link>
                        <router-link to="/fd/classes2">
                        <div class="course" style="display: inline-block">
                            <div>
                                <div style="width: 120px; height: 90px;">
                                    <img src="src/assets/images/frontdesk/front05.png" width="120" height="90" style="height: 90px; border-radius: 10px;">
                                </div>
                                <div><div>Java 入门教程从零基础到入门</div>
                                    <div>必修课</div>
                                </div>
                            </div>
                            <div>
                                <div role="progressbar" aria-valuenow="0" style="width: 270px;">
                                    <el-progress :percentage="0" />
                                </div><span>未学习</span></div>
                        </div>
                        </router-link>
                        <router-link to="/fd/classes3">
                        <div class="course" style="display: inline-block">
                            <div>
                                <div>
                                    <div style="width: 120px; height: 90px;">
                                        <img src="src/assets/images/frontdesk/front07.png" width="120" height="90" style="height: 90px; border-radius: 10px;"></div>
                                    <div>
                                        <div>React零基础入门到实战，完成企业级项目</div>
                                        <div>必修课</div>
                                    </div>
                                </div>
                                <div >
                                    <div role="progressbar" aria-valuenow="25">
                                        <el-progress :percentage="0" />
                                    </div>
                                    <span>未学习</span>
                                </div>
                            </div>
                        </div>
                        </router-link>
                        <router-link to="/fd/classes4">
                        <div class="course" style="display: inline-block">
                            <div>
                                <div>
                                    <div style="width: 120px; height: 90px;">
                                        <img src="../../../assets/images/frontdesk/front07.png" width="120" height="90" style="height: 90px; border-radius: 10px;">
                                    </div>
                                    <div><div>抖音短视频制作引流到盈利全攻略</div>
                                        <div>选修课</div>
                                    </div>
                                </div>
                                <div>
                                    <div role="progressbar" aria-valuenow="0" style="width: 270px;">
                                        <el-progress :percentage="0" />
                                    </div><span>未学习</span>
                                </div>
                            </div>
                        </div>
                        </router-link>
                    </div></el-tab-pane>
                    <el-tab-pane label="必修课" name="second">
                        <div class="course" style="display: inline-block">
                        <div>
                            <div style="width: 120px; height: 90px;">
                                <img src="../../../assets/images/frontdesk/front04.png" width="120" height="90" style="height: 90px; border-radius: 10px;">
                            </div>
                            <div>
                                <div>产品经理入门指南</div>
                                <div>必修课</div>
                            </div>
                        </div>
                        <div>
                            <div role="progressbar" aria-valuenow="0" style="width: 270px;">
                                <el-progress :percentage="0" />
                            </div>
                            <span>未学习</span>
                        </div>
                    </div>
                        <div class="course" style="display: inline-block">
                            <div>
                                <div style="width: 120px; height: 90px;">
                                    <img src="src/assets/images/frontdesk/front05.png" width="120" height="90" style="height: 90px; border-radius: 10px;">
                                </div>
                                <div><div>Java 入门教程从零基础到入门</div>
                                    <div>必修课</div>
                                </div>
                            </div>
                            <div>
                                <div role="progressbar" aria-valuenow="0" style="width: 270px;">
                                    <el-progress :percentage="0" />
                                </div><span>未学习</span></div>
                        </div>
                        <div class="course" style="display: inline-block">
                            <div>
                                <div>
                                    <div style="width: 120px; height: 90px;">
                                        <img src="src/assets/images/frontdesk/front07.png" width="120" height="90" style="height: 90px; border-radius: 10px;"></div>
                                    <div>
                                        <div>React零基础入门到实战，完成企业级项目</div>
                                        <div>必修课</div>
                                    </div>
                                </div>
                                <div >
                                    <div role="progressbar" aria-valuenow="25">
                                        <el-progress :percentage="0" />
                                    </div>
                                    <span>未学习%</span>
                                </div>
                            </div>
                        </div></el-tab-pane>
                    <el-tab-pane label="选修课" name="third"><div class="course" style="display: inline-block">
                        <div>
                            <div>
                                <div style="width: 120px; height: 90px;">
                                    <img src="../../../assets/images/frontdesk/front07.png" width="120" height="90" style="height: 90px; border-radius: 10px;">
                                </div>
                                <div><div>抖音短视频制作引流到盈利全攻略</div>
                                    <div>选修课</div>
                                </div>
                            </div>
                            <div>
                                <div role="progressbar" aria-valuenow="0" style="width: 270px;">
                                    <el-progress :percentage="0" />
                                </div><span>未学习</span>
                            </div>
                        </div>
                    </div></el-tab-pane>
                    <el-tab-pane label="已学完" name="fourth">已学完</el-tab-pane>
                    <el-tab-pane label="未学完" name="fivth">未学完</el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</div>

</template>

<style scoped>
#root{
    display: grid;
    justify-content: center;
    justify-items: center;
}
.father_top{
    display: grid;
    grid-template-columns: repeat(2, 1fr); /* 将容器分为两列 */
    gap: 10px; /* 设置间距 */
}
>>> .el-menu-top{
    margin-right: auto;
    margin-left: 31px;
}
.top_content{
    margin-top: 50px;
    margin-bottom: 50px;
    width: 588px;
    height: 132px;
    background: #ffffff;
    box-shadow: 0 4px 16px 4px #0000000a;
    border-radius: 12px;
    box-sizing: border-box;
    padding: 24px;
}
.count{
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}
.count1{
    margin-right: 40px;
    margin-left: 40px;
    margin-top: 20px;
    width: auto;
    font-size: 16px;
    font-weight: 600;
    color: #00000073;
    line-height: 24px;
    margin-right: 50px;
}
.top_picture{
    width: 36px;
    height: 36px;
    margin-right: 16px;
}
.typeface{
    font-size: 20px;
    font-weight: 600;
    color: #000000e0;
    line-height: 36px;
}
.course{
    display: flex;
    flex-wrap: wrap;
    width: 33%;
    height: 186px;
    background: #ffffff;
    border-radius: 12px;
    border: 2px solid #f6f6f6;
    box-sizing: border-box;
    padding: 24px;
    flex-direction: column;
    transition: all .3s;
    cursor: pointer;
}
.course:hover{
    box-shadow: 0 4px 16px 8px #0000000a;
    transition: all .3s;
    transform: translateY(-5px);
}
.block-col-2 .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    font-size: 14px;
    margin-bottom: 20px;
}
.h-6{
    margin-top: 50px;
    margin-bottom: 50px;

}
.demo-tabs > .el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
}
.block-col-2 .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    font-size: 14px;
    margin-bottom: 20px;
}
</style>