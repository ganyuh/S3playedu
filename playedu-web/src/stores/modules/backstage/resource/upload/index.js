import {defineStore} from 'pinia';
import SuperTask from "@/utils/superTask/index.js";

/**
 * uploadFile = {
 *     status: 1,
 *     file: 文件对象,
 *     fileName: '文件名称',
 *     fileSize: '文件大小（byte）',
 *     uploadProgress: 上传进度百分比（%）,
 *     timeLeft: 剩余时间（秒）,
 *     uploaded: '已上传大小（byte）,
 *     iconName: "zip"
 * }
 *
 */
const fileHashTask = new SuperTask(3);
const uploadTaskQueue = new SuperTask(3);
/**
 * 创建文件上传的 store
 */
export const useUploadStore = defineStore('upload', {
    state: () => ({
        uploadInfo: [],
        totalUploads: 10,
        fileHashTask,
        uploadTaskQueue
    })
});