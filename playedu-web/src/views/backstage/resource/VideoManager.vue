<script setup>
import {ref, reactive} from "vue";
import DialogSelector from "./components/upload/DialogSelector.vue";
import {useUploadStore} from "@/stores/modules/backstage/resource/upload/index.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {iconsNames} from '@/config/backstage/upload.js';
import {createFileChunks, extname, fileHash} from "@/utils/FileUtil.js";


const uploadStore = useUploadStore();

const dialogAttr = ref({
    showDialog: false,
    title: "上传视频",
    exts: [],
    maxSize: 5368709120, // 5GB
    maxLength: 10
});
const filesHandler = (event) => {
    console.log("filesHandler: ", event);
    ElMessageBox.confirm(
        '确定将这些视频上传至服务器吗?',
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            dialogAttr.value.showDialog = false;
            let files = Object.values(event);
            if (files.length <= 0) {
                ElMessage({
                    type: 'info',
                    message: '未选择文件',
                })
                return;
            }
            if (files.size + uploadStore.uploadInfo.filter(e => e.status !== 1).length > uploadStore.totalUploads) {
                ElMessage({
                    type: 'warning',
                    message: '文件数量过多',
                })
                return;
            }
            files.map(item => {
                if (!new Set(uploadStore.uploadInfo.map(item => item.fileName)).has(item.name)) {
                    let iconName = iconsNames[Object.keys(iconsNames).filter(key => key.includes(extname(item.name)))?.[0]]
                    // debugger
                    let uploadInfo = reactive({
                        fileName: item.name,
                        fileSize: item.size,
                        status: 1,
                        file: item,
                        uploadProgress: 0,
                        timeLeft: 0,
                        uploaded: 0,
                        iconName,
                        chunkInfo:{
                            fileChunks:[],
                            uploadedList:[]
                        }
                    });
                    uploadStore.uploadInfo.push(uploadInfo);
                    // uploadStore.fileHashTask.start();
                    uploadStore.fileHashTask.addTask(() => new Promise((resolve, reject) => {
                        uploadInfo.chunkInfo.fileChunks = createFileChunks(item);
                        fileHash(uploadInfo.chunkInfo.fileChunks).then(({hash}) => {
                            uploadInfo.fileHash = hash;
                            uploadInfo.status = 2;
                        })
                    })).then(() => {
                        // ElMessage({
                        //     type: 'success',
                        //     message: '文件哈希计算完毕! ',
                        // })
                    })
                }
            })
            ElMessage({
                type: 'success',
                message: '已加入上传任务队列',
            })
        })
        .catch((e) => {
            console.error("error: ", e)
            ElMessage({
                type: 'info',
                message: '文件未上传',
            })
        })
}
</script>

<template>
    <div class="video">
        <el-button type="primary" @click="dialogAttr.showDialog  = true">上传文件</el-button>
        <DialogSelector @processSelectedFiles="filesHandler" v-bind="dialogAttr"
                        @closeSelector="dialogAttr.showDialog = false">
            拖入视频文件或文件夹，点击上传
        </DialogSelector>
    </div>
</template>

<style scoped>

</style>