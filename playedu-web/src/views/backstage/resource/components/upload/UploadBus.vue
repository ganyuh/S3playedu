<script setup>
import {CloseBold, Sort} from "@element-plus/icons-vue";
import UploadEntry from "@views/backstage/resource/components/upload/UploadEntry.vue";
import {ref} from "vue";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import {useUploadStore} from "@/stores/modules/backstage/resource/upload/index.js";
import {doUploadFile} from "@/hooks/backstage/fileUploadBus/fileUploadBus.js";

const uploadStore = useUploadStore();
/**
 *  @description 默认收缩上传总线
 */
const packUp = ref(false);
const removeFile = (event) => {
    uploadStore.uploadInfo = uploadStore.uploadInfo.filter(e => e.fileName !== event.fileName)
    // console.log("removeFile: ", event)
}
const changeStatus = async (status, uploadInfo) => {
    if (uploadInfo.status === 1 || !uploadInfo.fileHash) {
        return;
    }
    uploadInfo.status = status;
    if (status === 3) {
        await doUploadFile(uploadInfo);
    }
}
// const doUploadFile = async (uploadInfo) => {
//     let {code, data, message} = await checkFileUploaded(uploadInfo.fileHash);
//     console.log("code: ", code, "data: ", data, "message: ", message);
//     let ext = extname(uploadInfo.fileName);
//     if (code === 213) {
//         // 文件不存在
//     } else if (code === 214) {
//         // 文件已存在，进行断点续传
//         uploadInfo.chunkInfo.uploadedList = data.chunkUploadedList;
//     } else {
//         // 其它报错为上传失败
//         uploadInfo.status = 5;
//         ElMessage({
//             showClose: true,
//             message: `${uploadInfo.fileName} 文件上传失败！`,
//             type: 'error',
//         })
//         return;
//     }
//     // 构建请求体
//     let formData = {
//         chunkNum: uploadInfo.chunkInfo.fileChunks.length,
//         fileHash: uploadInfo.fileHash,
//         fileSize: uploadInfo.fileSize,
//         chunkUploadedList: [],
//         originalName: uploadInfo.fileName,
//         contentType: ext
//     }
//     let uInitInfo = await uploadInit(formData);
//     console.log(formData.originalName + " - uInitInfo: ", uInitInfo);
//     uploadInfo.uploadId = uInitInfo.data.uploadId;
//     uploadInfo.urlList = uInitInfo.data.urlList;
//     // debugger
//     // let fileChunks = [...uploadInfo.chunkInfo.fileChunks];
//     uploadInfo.chunkInfo.fileChunks = [];
//     let fileChunks = createFileChunks(uploadInfo.file);
//     // console.log(formData.originalName + " - fileChunks: ", fileChunks);
//
//     try {
//         await uploadChunk(fileChunks, uploadInfo, uInitInfo);
//         let mergeInfo = await mergeUpload((({fileHash, uploadId}) => ({fileHash, uploadId}))(uploadInfo));
//         console.log("mergeInfo: ", mergeInfo);
//         console.log("current uploadInfo: ", uploadInfo);
//         if (mergeInfo.code === 200) {
//             let fileInfo = {
//                 "name": uploadInfo.fileName,
//                 "extension": extname(uploadInfo.fileName),
//                 "size": uploadInfo.fileSize,
//                 "fileId": uploadInfo.fileHash,
//                 "parentId": 0
//             }
//             if (uploadInfo.iconName === "video") {
//                 let data = {
//                     "duration": uploadInfo.posterInfo.duration,
//                     "video": {
//                         ...fileInfo
//                     },
//                     "frontCover": {
//                         "name": uploadInfo.posterInfo.videoName + ".png",
//                         "extension": "png",
//                         "size": uploadInfo.posterInfo.fileSize,
//                         "fileId": uploadInfo.posterInfo.videoName,
//                         "parentId": 0
//                     }
//                 }
//                 console.log("video data: ", data);
//                 let res = await putVideoResource(data);
//                 if (res.code === 200) {
//                     console.log("video resource upload success");
//                 } else {
//                     throw new Error();
//                 }
//             } else {
//                 console.log("fileInfo data: ", fileInfo);
//                 let res = await putResource(fileInfo);
//                 if (res.code === 200) {
//                     console.log("video resource upload success");
//                 } else {
//                     throw new Error();
//                 }
//             }
//             ElNotification({
//                 title: '上传成功',
//                 message: `${uploadInfo.fileName} 上传成功！`,
//                 type: 'success',
//             })
//             uploadInfo.status = 6;
//         }
//     } catch (e) {
//         uploadInfo.status = 5;
//         ElNotification({
//             title: '上传失败！',
//             message: `${uploadInfo.fileName} 上传失败！`,
//             type: 'error',
//         })
//     }
//
// }
// const uploadChunk = (fileChunks, uploadInfo, uInitInfo) => {
//     return new Promise(async (resolve, reject) => {
//         let successCount = 0
//         let totalChunks = 0
//         let posterUploaded = false;
//         fileChunks.map(async (chunkItem, index) => {
//             if (uploadInfo.chunkInfo.uploadedList.indexOf(index + 1) !== -1) {
//                 // 已上传完成的分块
//                 uploadInfo.chunkInfo.fileChunks.push({
//                     chunkIndex: index + 1,
//                     chunk: chunkItem,
//                     uploadUrl: uInitInfo.data?.urlList[index],
//                     progress: 1,
//                 })
//             } else {
//                 totalChunks++;
//                 // console.log(index + 1 , " - ", uInitInfo.data?.urlList[index])
//                 let temp = chunkItem;
//                 // console.log("chunkItem temp: ", temp)
//                 uploadInfo.chunkInfo.fileChunks.push({
//                     chunkIndex: index + 1,
//                     chunk: chunkItem,
//                     uploadUrl: uInitInfo.data?.urlList[index],
//                     progress: 0,
//                 })
//                 // await uploadTasks.addTask(() => new Promise((resolve, reject) => {
//                 // debugger
//                 try {
//                     const {status} = await axios.put(uInitInfo.data?.urlList[index], temp, {
//                         onUploadProgress: (progress) => {
//                             console.log(index + 1 + " chunk upload progress: ", progress)
//                             uploadInfo.chunkInfo.fileChunks[index].progress = progress.progress;
//                         },
//                         headers: {
//                             'Content-Type': 'application/octet-stream'
//                         }
//                     })
//                     if (status === 200) {
//                         successCount++;
//                         if (successCount >= totalChunks && posterUploaded)
//                             resolve();
//                     } else {
//                         reject();
//                     }
//                 } catch (e) {
//                     reject(e);
//                 }
//                 //     .then(e=>{
//                 //     // uploadInfo.chunkInfo.fileChunks[index].
//                 //     // reject(e);
//                 // })
//                 // uploadFile(uInitInfo.data?.urlList[index], temp, {
//                 //     onUploadProgress: (progress) => {
//                 //         console.log(index + 1 + " chunk upload progress: ", progress)
//                 //         // uploadInfo.chunkInfo.fileChunks[index].progress = progress;
//                 //     }
//                 // }).then(resolve, reject)
//                 // }))
//             }
//         })
//         if (uploadInfo.iconName === "video") {
//             try {
//                 let {blob, duration} = await parseVideo(uploadInfo.file);
//                 let posterInfo = {
//                     videoName: uploadInfo.fileHash + ".poster",
//                     fileSize: blob.size,
//                     ext: "png",
//                     duration
//                 };
//                 let response = await singleUpload(blob, posterInfo);
//                 if (response.code === 200) {
//                     uploadInfo.posterInfo = posterInfo;
//                     posterUploaded = true;
//                 }
//                 if (successCount >= totalChunks && posterUploaded)
//                     resolve();
//                 else
//                     reject();
//                 console.log("上传封面 response", response);
//             } catch (e) {
//             }
//         }else{
//             posterUploaded = true;
//         }
//         if (totalChunks === 0) {
//             resolve();
//         }
//     });
// }
/**
 * 关闭上传窗口，终止上传
 */
const closeUploadBus = () => {
    ElMessageBox.confirm(
        '还有文件未上传，是否关闭窗口？',
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            // 终止上传文件，关闭窗口

        })
        .catch(() => {
        })
}
// let uploadInfo = computed(()=>uploadStore.uploadInfo);
// watch(uploadInfo, (newValue, oldValue)=>{
//     console.log('count changed from', oldValue, 'to', newValue);
// }, {deep:true})
</script>

<template>
    <div class="upload-container" :style="`height:${packUp?'90px;':'560px'}`">
        <el-container style="height:100%">
            <el-header @click="packUp = !packUp" class="el-header my-pointer">
                <el-row class="row-bg" justify="space-between">
                    <el-col :span="9">
                        <el-icon>
                            <Sort/>
                        </el-icon>
                        <span>上传进度：65.15%</span></el-col>
                    <el-col :span="3" style="justify-content: flex-end;">
                        <span><svg viewBox="64 64 896 896" focusable="false" data-icon="fullscreen" width="1em"
                                   height="1em" fill="currentColor" aria-hidden="true"><path
                                d="M290 236.4l43.9-43.9a8.01 8.01 0 00-4.7-13.6L169 160c-5.1-.6-9.5 3.7-8.9 8.9L179 329.1c.8 6.6 8.9 9.4 13.6 4.7l43.7-43.7L370 423.7c3.1 3.1 8.2 3.1 11.3 0l42.4-42.3c3.1-3.1 3.1-8.2 0-11.3L290 236.4zm352.7 187.3c3.1 3.1 8.2 3.1 11.3 0l133.7-133.6 43.7 43.7a8.01 8.01 0 0013.6-4.7L863.9 169c.6-5.1-3.7-9.5-8.9-8.9L694.8 179c-6.6.8-9.4 8.9-4.7 13.6l43.9 43.9L600.3 370a8.03 8.03 0 000 11.3l42.4 42.4zM845 694.9c-.8-6.6-8.9-9.4-13.6-4.7l-43.7 43.7L654 600.3a8.03 8.03 0 00-11.3 0l-42.4 42.3a8.03 8.03 0 000 11.3L734 787.6l-43.9 43.9a8.01 8.01 0 004.7 13.6L855 864c5.1.6 9.5-3.7 8.9-8.9L845 694.9zm-463.7-94.6a8.03 8.03 0 00-11.3 0L236.3 733.9l-43.7-43.7a8.01 8.01 0 00-13.6 4.7L160.1 855c-.6 5.1 3.7 9.5 8.9 8.9L329.2 845c6.6-.8 9.4-8.9 4.7-13.6L290 787.6 423.7 654c3.1-3.1 3.1-8.2 0-11.3l-42.4-42.4z"></path></svg></span>
                        <span @click.stop="closeUploadBus" style="margin-left: 20px"><el-icon><CloseBold/></el-icon></span>
                    </el-col>
                </el-row>
            </el-header>
            <el-main>
                <!--  v-show="!packUp" -->
                <div class="upload-list">
                    <UploadEntry v-for="(i,index) in uploadStore.uploadInfo" :key="index"
                                 @changeStatus="changeStatus($event, i)"
                                 @removeFile="removeFile" v-bind="i"/>
                </div>
                <div class="pl" v-show="!uploadStore.uploadInfo || uploadStore.uploadInfo.length <= 0">暂无文件上传
                </div>
            </el-main>
            <el-footer @click="packUp = !packUp" class="my-pointer">
                <div>{{ packUp ? '展开' : '收起' }}</div>
            </el-footer>
        </el-container>
    </div>
</template>

<style scoped>

.upload-container {
    background: #fff;
    width: 420px;
    position: fixed;
    right: 0;
    bottom: 0;
    z-index: 1999;
    margin: 20px;
    border-radius: 5px;
    box-shadow: 0 0 7px 2px #cedbe8;
    transition: 0.3s;
}

/**
//max-height: 585px; height: 585px; //min-height: 45px; width: 432px; background: #fff;
 */
>>> .el-main {
    max-height: 475px;
    --el-main-padding: 0px;
    background: #fafafa;
}

.el-header >>> .el-row .el-col {
    display: flex;
    align-items: center;
}

.el-header {
    transition: 0.3s;
    height: 45px;
    border-bottom: 1px solid #eaeaea;
}

.el-footer.my-pointer:hover,
.el-header:hover {
    background: #f5f5f6;
}

.el-header .row-bg {
    height: 100%;
}

.el-header .el-col.el-col-6 {
    display: flex;
    align-items: center;
    justify-content: space-around;
}

.upload-list {
    background: #fafafa;
}

.el-footer {
    transition: 0.3s;
    --el-footer-height: 45px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.el-main .pl {
    --margin-w: 10px;
    margin: var(--margin-w);
    width: calc(100% - var(--margin-w) * 2);
    height: calc(100% - var(--margin-w) * 2);
    display: flex;
    align-items: center;
    justify-content: center;
    background: #fff;
    color: #acacac;
    font-size: 24px;
    border-radius: 7px;
    border: 1px dashed #dfdfdf;
}
</style>