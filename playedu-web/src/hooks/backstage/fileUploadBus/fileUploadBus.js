import {checkFileUploaded, mergeUpload, singleUpload, uploadInit} from "@/service/modules/backstage/resource/upload.js";
import {createFileChunks, extname, parseVideo} from "@/utils/FileUtil.js";
import {ElMessage, ElNotification} from "element-plus";
import {putResource, putVideoResource} from "@/service/modules/backstage/resource/resource.js";
import axios from "axios";

export const doUploadFile = async (uploadInfo) => {
    let {code, data, message} = await checkFileUploaded(uploadInfo.fileHash);
    console.log("code: ", code, "data: ", data, "message: ", message);
    let ext = extname(uploadInfo.fileName);
    if (code === 213) {
        // 文件不存在
    } else if (code === 214) {
        // 文件已存在，进行断点续传
        uploadInfo.chunkInfo.uploadedList = data.chunkUploadedList;
    } else {
        // 其它报错为上传失败
        uploadInfo.status = 5;
        ElMessage({
            showClose: true,
            message: `${uploadInfo.fileName} 文件上传失败！`,
            type: 'error',
        })
        return;
    }
    // 构建请求体
    let formData = {
        chunkNum: uploadInfo.chunkInfo.fileChunks.length,
        fileHash: uploadInfo.fileHash,
        fileSize: uploadInfo.fileSize,
        chunkUploadedList: [],
        originalName: uploadInfo.fileName,
        contentType: ext
    }
    let uInitInfo = await uploadInit(formData);
    console.log(formData.originalName + " - uInitInfo: ", uInitInfo);
    uploadInfo.uploadId = uInitInfo.data.uploadId;
    uploadInfo.urlList = uInitInfo.data.urlList;
    // debugger
    // let fileChunks = [...uploadInfo.chunkInfo.fileChunks];
    uploadInfo.chunkInfo.fileChunks = [];
    let fileChunks = createFileChunks(uploadInfo.file);
    // console.log(formData.originalName + " - fileChunks: ", fileChunks);

    try {
        await uploadChunk(fileChunks, uploadInfo, uInitInfo);
        let mergeInfo = await mergeUpload((({fileHash, uploadId}) => ({fileHash, uploadId}))(uploadInfo));
        console.log("mergeInfo: ", mergeInfo);
        console.log("current uploadInfo: ", uploadInfo);
        if (mergeInfo.code === 200) {
            let fileInfo = {
                "name": uploadInfo.fileName,
                "extension": extname(uploadInfo.fileName),
                "size": uploadInfo.fileSize,
                "fileId": uploadInfo.fileHash,
                "parentId": 0
            }
            if (uploadInfo.iconName === "video") {
                let data = {
                    "duration": uploadInfo.posterInfo.duration,
                    "video": {
                        ...fileInfo
                    },
                    "frontCover": {
                        "name": uploadInfo.posterInfo.videoName + ".png",
                        "extension": "png",
                        "size": uploadInfo.posterInfo.fileSize,
                        "fileId": uploadInfo.posterInfo.videoName,
                        "parentId": 0
                    }
                }
                console.log("video data: ", data);
                let res = await putVideoResource(data);
                if (res.code === 200) {
                    console.log("video resource upload success");
                } else {
                    throw new Error();
                }
            } else {
                console.log("fileInfo data: ", fileInfo);
                let res = await putResource(fileInfo);
                if (res.code === 200) {
                    console.log("video resource upload success");
                } else {
                    throw new Error();
                }
            }
            ElNotification({
                title: '上传成功',
                message: `${uploadInfo.fileName} 上传成功！`,
                type: 'success',
            })
            uploadInfo.status = 6;
        }
    } catch (e) {
        uploadInfo.status = 5;
        ElNotification({
            title: '上传失败！',
            message: `${uploadInfo.fileName} 上传失败！`,
            type: 'error',
        })
    }

}
export const uploadChunk = (fileChunks, uploadInfo, uInitInfo) => {
    return new Promise(async (resolve, reject) => {
        let successCount = 0
        let totalChunks = 0
        let posterUploaded = false;
        fileChunks.map(async (chunkItem, index) => {
            if (uploadInfo.chunkInfo.uploadedList.indexOf(index + 1) !== -1) {
                // 已上传完成的分块
                uploadInfo.chunkInfo.fileChunks.push({
                    chunkIndex: index + 1,
                    chunk: chunkItem,
                    uploadUrl: uInitInfo.data?.urlList[index],
                    progress: 1,
                })
            } else {
                totalChunks++;
                // console.log(index + 1 , " - ", uInitInfo.data?.urlList[index])
                let temp = chunkItem;
                // console.log("chunkItem temp: ", temp)
                uploadInfo.chunkInfo.fileChunks.push({
                    chunkIndex: index + 1,
                    chunk: chunkItem,
                    uploadUrl: uInitInfo.data?.urlList[index],
                    progress: 0,
                })
                // await uploadTasks.addTask(() => new Promise((resolve, reject) => {
                // debugger
                try {
                    const {status} = await axios.put(uInitInfo.data?.urlList[index], temp, {
                        onUploadProgress: (progress) => {
                            console.log(index + 1 + " chunk upload progress: ", progress)
                            uploadInfo.chunkInfo.fileChunks[index].progress = progress.progress;
                        },
                        headers: {
                            'Content-Type': 'application/octet-stream'
                        }
                    })
                    if (status === 200) {
                        successCount++;
                        if (successCount >= totalChunks && posterUploaded)
                            resolve();
                    } else {
                        reject();
                    }
                } catch (e) {
                    reject(e);
                }
                //     .then(e=>{
                //     // uploadInfo.chunkInfo.fileChunks[index].
                //     // reject(e);
                // })
                // uploadFile(uInitInfo.data?.urlList[index], temp, {
                //     onUploadProgress: (progress) => {
                //         console.log(index + 1 + " chunk upload progress: ", progress)
                //         // uploadInfo.chunkInfo.fileChunks[index].progress = progress;
                //     }
                // }).then(resolve, reject)
                // }))
            }
        })
        if (uploadInfo.iconName === "video") {
            try {
                let {blob, duration} = await parseVideo(uploadInfo.file);
                let posterInfo = {
                    videoName: uploadInfo.fileHash + ".poster",
                    fileSize: blob.size,
                    ext: "png",
                    duration
                };
                let response = await singleUpload(blob, posterInfo);
                if (response.code === 200) {
                    uploadInfo.posterInfo = posterInfo;
                    posterUploaded = true;
                }
                if (successCount >= totalChunks && posterUploaded)
                    resolve();
                else
                    reject();
                console.log("上传封面 response", response);
            } catch (e) {
            }
        }else{
            posterUploaded = true;
        }
        if (totalChunks === 0) {
            resolve();
        }
    });
}