import http from "@/service";

/**
 * 保存普通资源信息
 * data:{
 *     "name": "test.mp4",
 *     "extension": "mp4",
 *     "size": 78456456,
 *     "fileId": "fileHash",
 *     "parentId": 0
 * }
 */
export const putResource = (data) => {
    return http.post("/manage/resource/save", data);
}

/**
 *  保存视频资源信息
 *  data:{
 *     "duration": 432000, # 视频时长
 *     "video": {
 *         "name": "test.mp4",
 *         "extension": "mp4",
 *         "size": 78456456,
 *         "fileId": "videoFileHash",
 *         "parentId": 0
 *     },
 *     "frontCover": {
 *         "name": "test.png",
 *         "extension": "png",
 *         "size": 156456,
 *         "fileId": "frontCoverFileHash",
 *         "parentId": 0
 *     }
 *  }
 */
export const putVideoResource = (data) => {
    return http.post("/manage/resource/saveVideo", data);
}