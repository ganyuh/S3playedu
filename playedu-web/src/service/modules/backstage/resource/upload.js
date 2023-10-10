import http from "@/service";
import qs from "qs";

/**
 *  根据文件 hash 判断上传上传至服务器情况
 * @param fileHash 文件 hash
 * @returns {*}
 */
export const checkFileUploaded = (fileHash) => {
    return http.get(`/manage/upload/check/${fileHash}`)
}

export const uploadInit = (fileUploadInfo) =>{
    return http.post('/manage/upload/init', fileUploadInfo);
}

export const uploadFile = (url ,fileUploadInfo, _config) => {
    return http.put(url, fileUploadInfo, {headers:{'Content-Type': 'application/octet-stream'},..._config});
}

export const mergeUpload = (fileUploadInfo) => {
    return http.post("/manage/upload/merge", fileUploadInfo,{timeout: 1000000})
}

export const singleUpload = (blob, params) => {
    return http.post(`/manage/upload/single?${qs.stringify(params)}`, blob, {headers:{'Content-Type': 'application/octet-stream'}});
}
// fileHash=${fileHash}&fileSize=${blob.size}&ext=png