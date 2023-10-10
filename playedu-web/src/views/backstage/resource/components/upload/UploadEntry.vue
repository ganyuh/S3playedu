<script setup>
import {CaretRight, CloseBold, Refresh} from "@element-plus/icons-vue";
import {fileSize as formatFileSize} from "@/utils/FileUtil.js";

import {uploadStatus} from "@/config/backstage/upload.js";
import {computed, ref} from "vue";
import {ElMessageBox} from "element-plus";

const props = defineProps({
    status: {
        type: Number,
        default: 4
    },
    uploadProgress: {
        type: Number,
        default: 0.00
    },
    fileName: {
        type: String,
        default: ''
    },
    fileSize: {
        type: Number,
        default: 0.00
    },
    timeLeft: {
        type: Number,
        default: 0
    },
    uploaded: {
        type: Number,
        default: 0.00
    },
    iconName: {
        type: String,
        default: 'other'
    },
    file: {
        type: Object,
        default: () => {
        }
    },
    fileChunks: {
        type: Array,
        default: () => []
    },
    fileHash: {
        type: String,
        default: ''
    },
    chunkInfo:{
        type: Object,
        default: ()=>({})
    }
})

/**
 * 计算属性
 */
const fileSize = computed(() => {
    return formatFileSize(props.fileSize);
})
const uploaded = computed(() => {
    return formatFileSize(props.uploaded);
})
const timeLeft = computed(() => {
    return timeUnitConversion(props.timeLeft);
})
/**
 * 时间单位转换
 */
const timeUnitConversion = (timestamp) => {
    if (timestamp < 1) {
        return 0;
    }
    if (timestamp < 60) {
        return timestamp.toFixed(0) + '秒';
    }
    if (timestamp < 3600) {
        return Math.floor(timestamp / 60).toFixed(0) + '分钟';
    }
    if (timestamp < 86400) {
        return Math.floor(timestamp / 3600).toFixed(0) + ' 小时';
    }
    // if(timestamp < 2592000){
    //     return Math.floor(timestamp/86400).toFixed(0) + ' 天';
    // }
    return Math.floor(timestamp / 86400).toFixed(0) + 'd';
}

const chunkInfo = computed(() => {
    const chunksInfo = props.chunkInfo || [];
    let progress = 0;
    let i = 0;
    console.log("chunksInfo: ", chunksInfo)
    for (i = 0; i < chunksInfo.length; i++) {
        console.log("chunksInfo: ", chunksInfo[i])
        progress += chunksInfo[i]?.progress || 0;
    }
    let p = progress / (i + 1) * 100;
    console.log("uploadProgress: ", p)
    return p;
})
const emits = defineEmits(['removeFile', 'changeStatus'])
// const curStatus = computed(() => getUploadStatus(props.status));
const curStatus = computed(() => uploadStatus.get(props.status));

const colors = [
    {color: '#f56c6c', percentage: 20},
    {color: '#e6a23c', percentage: 40},
    {color: '#6f7ad3', percentage: 60},
    {color: '#1989fa', percentage: 80},
    {color: '#00ab38', percentage: 100},
]
const url = ref(`@/assets/images/upload/other-icon.ico`);

// console.log("props iconName: ", props.iconName)
url.value = new URL(`../../../../../assets/images/upload/${props.iconName}-icon.ico`, import.meta.url).href;
// console.log("url: ", url.value)
if (url.value === `${window.location.origin}/undefined`) {
    url.value = new URL(`../../../../../assets/images/upload/other-icon.ico`, import.meta.url).href;
}
// console.log("url: ", url.value)
const removeFile = () => {
    if (props.status !== 6) {
        ElMessageBox.confirm(
            '确定取消上传该文件吗?',
            '警告',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(() => {
                emits('removeFile', {...props})
            })
            .catch(() => {
            })
    } else {
        // props.status === 1; 表示文件已上传至服务器
        emits('removeFile', {...props})
    }
}
const changeStatus = () => {
    // 上传完成则不许改变状态
    if (props.status === 6)
        return;
    emits('changeStatus', curStatus.value.next)
}

</script>

<template>
    <div class="upload-item">
        <div class="left-info">
            <div class="upload-icon my-pointer" @click="changeStatus">
                <!-- 上传暂停 -->
                <div class="upload-status">
                    <!-- 已暂停 -->
                    <el-progress v-if="props.status === 4" class="mask" :width="40" :stroke-width="2" type="circle"
                                 :percentage="chunkInfo">
                        <el-icon>
                            <CaretRight/>
                        </el-icon>
                    </el-progress>
                    <!-- 上传中 -->
                    <el-progress v-if="props.status === 3" class="mask" :color="colors" :width="40" :stroke-width="2"
                                 type="circle"
                                 :percentage="chunkInfo">
                        <svg class="icon"
                             style="width: 1em;height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;"
                             viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
                            <path d="M341.332992 142.222336c47.128576 0 85.334016 38.20544 85.334016 85.332992v568.889344c0 47.128576-38.20544 85.332992-85.334016 85.332992-47.127552 0-85.332992-38.20544-85.332992-85.332992V227.555328c0-47.128576 38.20544-85.332992 85.332992-85.332992z m341.334016 0c47.127552 0 85.332992 38.20544 85.332992 85.332992v568.889344c0 47.128576-38.20544 85.332992-85.332992 85.332992-47.128576 0-85.334016-38.20544-85.334016-85.332992V227.555328c0-47.128576 38.20544-85.332992 85.334016-85.332992z"></path>
                        </svg>
                    </el-progress>
                    <!-- 上传失败 -->
                    <el-icon v-if="props.status === 5" class="mask">
                        <Refresh/>
                    </el-icon>
                    <!-- 等待上传 -->
                    <span v-if="props.status === 2 || props.status === 1" class="mask">
                        <svg class="icon"
                             style="width: 1em;height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;"
                             viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"><path
                                d="M512 192c212.077 0 384 171.923 384 384S724.077 960 512 960 128 788.077 128 576s171.923-384 384-384z m0 64c-176.731 0-320 143.269-320 320s143.269 320 320 320 320-143.269 320-320-143.269-320-320-320z m-32 128c35.346 0 64 28.654 64 64v96h96c34.993 0 63.426 28.084 63.991 62.942L704 608H512c-17.673 0-32-14.327-32-32V384zM688 80c0 35.346-28.654 64-64 64H336c0-35.346 28.654-64 64-64h288z"
                        ></path>
                        </svg>
                    </span>
                    <!-- 上传完成 -->
                    <div>
                        <img :src="url" alt="">
                    </div>
                </div>
            </div>
            <div class="info">
                <div class="file-name" :title="props.fileName">{{ props.fileName }}</div>
                <div class="rate"><span>{{ fileSize || "未知" }} / {{ uploaded || "未知" }}，</span>
                    <span>剩余时间：{{
                        timeLeft || "未知"
                        }}</span></div>
            </div>
        </div>
        <div class="upload-tag">
            <el-tag class="ml-2" :type="curStatus?.type">{{ curStatus?.name }}</el-tag>
        </div>
        <div class="my-pointer" @click="removeFile">
            <el-icon>
                <CloseBold/>
            </el-icon>
        </div>
    </div>
    <el-divider/>
</template>

<style scoped>
.upload-item,
.upload-item .left-info {
    display: flex;
    align-items: center;
}

.upload-item {
    padding: 15px;
    justify-content: space-between;
}

/**

//.upload-item .right{
//    justify-content: space-between;
//}
 */
.upload-item .left-info {
}

.upload-item .left-info .info {
    font-size: 16px;
}

.upload-item .left-info .info > * {
    width: 200px;
    max-width: 200px;
}

.upload-item .left-info .info .file-name {
    line-height: 21px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
}

.upload-item .left-info .info .rate {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    font-size: 12px;
    color: #757575;
    line-height: 21px;
}

.upload-item .left-info .info .rate span {
    height: 21px;
    flex: none;
}

.upload-item .left-info .upload-icon {
    margin-right: 10px;
    height: 64px;
    width: 60px;
    position: relative;
    border-radius: 5px;
}

/**
//.upload-item .left-info .upload-icon .upload-status {
//}
**/
.upload-item .left-info .upload-icon .upload-status >>> .el-progress__text {
    font-size: 27px !important;
    color: inherit;
}

.upload-item .left-info .upload-icon .uploading-error >>> .el-icon {
//width: 20px; //height: 20px;
}

.upload-item .left-info .upload-icon .upload-status .mask {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 40px;
    color: #fff;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    border-radius: 10px;
}

.upload-item .left-info .upload-icon img {
    width: 60px;

}

/**
  设置分割线的边距
 */
.el-divider--horizontal {
    margin: 0;
}
</style>