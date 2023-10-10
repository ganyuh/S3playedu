<script setup>
    import TangIcon from "@components/TangIcon.vue";
    import { ref, onMounted } from "vue";
    //  按需引入 echarts
    import * as echarts from "echarts";
    const chartDom = ref() // 使用ref创建虚拟DOM引用，使用时用chartDom.value
    onMounted(
        () => {
            init()
        }
    )
    function init() {
        // 基于准备好的dom，初始化echarts实例
        const myChart = echarts.init(chartDom.value);
        // 指定图表的配置项和数据
        const option = {
            tooltip: {
                title:'总视频数',
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                left: 'center',
                top: 'bottom',
                data: [
                    'rose1',
                    'rose2',
                ]
            },
            toolbox: {
                show: true,
                feature: {
                    mark: { show: true },
                    dataView: { show: true, readOnly: false },
                    restore: { show: true },
                    saveAsImage: { show: true }
                }
            },
            series: [
                {
                    name: '',
                    type: 'pie',
                    radius: [55, 140],
                    center: ['55%', '50%'],
                    roseType: 'area',
                    itemStyle: {
                        borderRadius: 5
                    },
                    label: {
                        show: false
                    },
                    emphasis: {
                        label: {
                            show: true
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        { value: 0, name: '图片数' },
                        { value: 0, name: '视频数' },

                    ]
                },
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }
</script>


<template>
    <div class="top_whole" top_whole>
        <div class="whole1">
            <el-row>
                <el-col :span="6">
                    <el-statistic title="今日学习学员" :value="0"/>
                </el-col>
                <el-col :span="6">
                    <el-statistic title="总学员数" :value="0"/>
                </el-col>
                <el-col :span="6">
                    <el-statistic title="线上课数" :value="0"/>
                </el-col>
            </el-row>
        </div>
        <div class="whole1">
            <el-col :span="6">
                <el-statistic title="部门数" :value="0"/>
            </el-col>
            <el-col :span="6">
                <el-statistic title="分类数" :value="0"/>
            </el-col>
            <el-col :span="6">
                <el-statistic title="管理员" :value="0"/>
            </el-col>
        </div>
        <div class="whole2">
            <div><span>快捷操作</span>
                <div class="father_course">
                    <div class="course">
                        <router-link to="b/student"><div>
                            <div>
                                <TangIcon class-name="icon-user" class="icon1"/>

                            </div>
                        </div>添加学员</router-link>
                    </div>
                    <div class="course">
                        <router-link to="b/resource-manage/video">
                        <div>
                            <div>
                                <TangIcon class-name="icon-upvideo" class="icon2"/>

                            </div>
                        </div>上传视频</router-link>
                    </div>
                    <div class="course">
                        <router-link to="b/classes">
                        <div>
                            <div>
                                <TangIcon class-name="icon-onlinelesson" class="icon3"/>

                            </div>
                        </div>线上课</router-link>
                    </div>
                    <div class="course">
                        <router-link to="b/dept">
                        <div>
                            <div>
                                <TangIcon class-name="icon-department" class="icon4"/>

                            </div>
                        </div>新建部门</router-link>
                    </div>
                </div>
            </div>
        </div>
        <div class="whole2">
            <div class="document"><span>产品文档</span>
                <div><img src="src/assets/images/backdesk/documentation.png" class="wdimg" alt></div>
                <div>
                    <div class="document_link"><a href="https://www.playedu.xyz/docs/docs/guide/">点击查看产品文档，快速玩转Playedu！
                        <img src="src/assets/images/backdesk/jump.png" style="width: 16px;height: 16px;"></a></div>
                </div>
            </div>
        </div>
        <div class="whole3">
            <div><span>今日学习排行</span>
                <div>
                    <div>
                        <div class="rank_details">
                            <div>
                                <img src="../../../assets/images/backdesk/rankimg1.png"
                                     style="width: 24px;height: 24px;margin-right: 8px">
                            </div>
                        </div>
                        <div class="rank_details">
                            <div>
                                <img src="../../../assets/images/backdesk/rankimg2.png"
                                     style="width: 24px;height: 24px;margin-right: 8px">
                            </div>
                        </div>
                        <div class="rank_details">
                            <div>
                                <img src="../../../assets/images/backdesk/rankimg3.png"
                                     style="width: 24px;height: 24px;margin-right: 8px">
                            </div>
                        </div>
                        <div></div>
                        <div></div>
                    </div>
                </div>

            </div>
        </div>
        <div class="whole3">
            <div><span>资源统计</span>
                <div class="Echarts">
                    <div ref="chartDom" id="main" style="width: 400px;height:200px;"></div>
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
    .top_whole {
        display: flex;
        flex-wrap: wrap;
    }

    .whole1 {
        flex-wrap: wrap;
        width: 50%;
        height: 120px;
        background: #ffffff;
        border-radius: 12px;
        border: 2px solid #f6f6f6;
        box-sizing: border-box;
        padding: 24px;
        display: flex;
        flex-direction: column;
        transition: all .3s;
        margin-top: 20px;
    }

    .whole2 {

        flex-wrap: wrap;
        width: 50%;
        height: 186px;
        background: #ffffff;
        border-radius: 12px;
        border: 2px solid #f6f6f6;
        box-sizing: border-box;
        padding: 24px;
        display: flex;
        flex-direction: column;
        transition: all .3s;
        margin-top: 20px;
    }

    .father_course {
        display: flex;
        flex-wrap: wrap;
    }

    .course {
        display: flex;
        flex-wrap: wrap;
        width: 20%;
        margin: auto 10px auto 10px;
        height: 86px;
        background: #ffffff;
        border-radius: 12px;
        border: 2px solid #ffffff;
        box-sizing: border-box;
        padding: 24px;
        display: flex;
        flex-direction: column;
        transition: all .3s;
        cursor: pointer;
    }

    .course:hover {
        box-shadow: 0 4px 16px 8px #0000000a;
        transition: all .3s;
        transform: translateY(-5px);
    }

    .icon1 {
        color: rgb(255, 159, 50);
        font-size: 36px;
    }

    .icon2 {
        color: rgb(65, 159, 255);
        font-size: 36px;
    }

    .icon3 {
        color: rgb(178, 132, 255);
        font-size: 36px;
    }

    .icon4 {
        color: rgb(33, 199, 133);
        font-size: 36px;
    }

    .document {
        width: 100%;
        height: auto;
        float: left;
        background-color: #fff;
        box-sizing: border-box;
        padding: 24px 36px;
        border-radius: 12px;
        position: relative;
    }

    .document_link {
        position: absolute;
        left: calc(20% + 180px);
        width: auto;
        height: 24px;
        line-height: 24px;
        font-size: 14px;
        font-weight: 400;
        color: #00000073;
        display: flex;
        align-items: center;
        cursor: pointer;
        margin-top: 60px;
    }

    .document_link:hover {
        text-decoration: underline;
    }

    .wdimg {
        width: 130px;
        height: 130px;
        position: absolute;
        top: 22px;
        left: 20%;
        z-index: 10;
    }

    .whole3 {

        flex-wrap: wrap;
        width: 50%;
        height: 300px;
        background: #ffffff;
        border-radius: 12px;
        border: 2px solid #f6f6f6;
        box-sizing: border-box;
        padding: 24px;
        display: flex;
        flex-direction: column;
        transition: all .3s;
        margin-top: 20px;
    }

    .rank_details {
        margin-top: 16px;
    }
    #main{
        margin: auto;
    }
</style>