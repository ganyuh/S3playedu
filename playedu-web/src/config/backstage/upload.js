export const uploadStatus = new Map([
    [
        1,
        {
            "name": "文件校验中",
            "value": "fileVerify",
            "type": "info",
            "next": 2
        }
    ],
    [
        2,
        {
            "name": "等待上传",
            "value": "waiting",
            "type": "info",
            "next": 3
        }
    ],
    [
        3,
        {
            "name": "上传中",
            "value": "uploading",
            "type": "",
            "next": 4
        }
    ],
    [
        4,
        {
            "name": "已暂停",
            "value": "waiting",
            "type": "warning",
            "next": 2
        }
    ],
    [
        5,
        {
            "name": "上传失败",
            "value": "failed",
            "type": "danger",
            "next": 2
        }
    ],
    [
        6,
        {
            "name": "上传完成",
            "value": "completed",
            "type": "success"
        }
    ]
])
/**
 * 支持一次上传多个 / 支持word、excel、ppt、pdf、zip、rar、txt格式文件
 */
export const iconsNames = {
    "png|jpg|jpeg|gif":"image",
    "mp4":"video",
    "zip|rar":"zip",
    "word|excel|ppt|pdf":"wps",
    "*":"other"
};
/**
 * 根据状态 id 获取具体状态信息
 * @param id
 * @deprecated
 */
// export const getUploadStatus = (id) => uploadStatus.filter((item) => item.id === id)?.[0];