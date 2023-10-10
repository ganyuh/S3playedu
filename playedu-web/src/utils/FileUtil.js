import SparkMD5 from "spark-md5";

/**
 * @param: fileName - 文件名称
 * @param: 数据返回 1) 无后缀匹配 - false
 * @param: 数据返回 2) 匹配图片 - image
 * @param: 数据返回 3) 匹配 txt - txt
 * @param: 数据返回 4) 匹配 excel - excel
 * @param: 数据返回 5) 匹配 word - word
 * @param: 数据返回 6) 匹配 pdf - pdf
 * @param: 数据返回 7) 匹配 ppt - ppt
 * @param: 数据返回 8) 匹配 视频 - video
 * @param: 数据返回 9) 匹配 音频 - radio
 * @param: 数据返回 10) 其他匹配项 - other
 *
 **/
export const fileSuffixTypeUtil = (fileName) => {
    // 后缀获取
    let suffix = extname(fileName);
    // 获取类型结果
    let result = "";
    // fileName无后缀返回 false
    if (!suffix) {
        return false;
    }
    // 图片格式
    let imgList = ["png", "jpg", "jpeg", "bmp", "gif"];
    // 进行图片匹配
    result = imgList.some((item) => item === suffix);
    if (result) {
        result = "image";
        return result;
    }
    // 匹配txt
    let txtList = ["txt"];
    result = txtList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "txt";
        return result;
    }
    // 匹配 excel
    let excelList = ["xls", "xlsx"];
    result = excelList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "excel";
        return result;
    }
    // 匹配 word
    let wordList = ["doc", "docx"];
    result = wordList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "word";
        return result;
    }
    // 匹配 pdf
    let pdfList = ["pdf"];
    result = pdfList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "pdf";
        return result;
    }
    // 匹配 ppt
    let pptList = ["ppt"];
    result = pptList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "ppt";
        return result;
    }
    // 匹配 视频
    let videoList = ["mp4", "m2v", "mkv", "ogg", "flv", "avi", "wmv", "rmvb"];
    result = videoList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "video";
        return result;
    }
    // 匹配 音频
    let radioList = ["mp3", "wav", "wmv"];
    result = radioList.some(function (item) {
        return item === suffix;
    });
    if (result) {
        result = "radio";
        return result;
    }
    // 其他 文件类型
    result = "other";
    return result;
}

/**
 * 计算文件 Hash 值
 * @param chunks {Blob[]}
 */
export const fileHash = (chunks) => {
    return new Promise((resolve, reject) => {
        let index = 0
        try {
            const spark = new SparkMD5();
            const _read = (i) => {
                index = i;
                if (i >= chunks.length) {
                    // console.log("spark: ", spark.end())
                    resolve({
                        hash: spark.end()
                    }); //文件数据读完
                    return;
                }
                const blob = chunks[i];
                const reader = new FileReader();
                reader.onload = e => {
                    // 拿到当前读取到的字节数组
                    spark.append(e.target.result)
                    _read(i + 1)
                }
                reader.readAsArrayBuffer(blob);
            }
            _read(0)
        } catch (e) {
            reject(e)
        }
    })
}

/**
 * 计算文件分片
 * @param file {File}
 * @param chunkSize {Number} default 5MB (unit byte)
 * @returns {Blob[]}
 */
export const createFileChunks = (file, chunkSize = 5242880) => {
    const chunks = [];
    for (let i = 0; i < file.size; i += chunkSize) {
        chunks.push(file.slice(i, i + chunkSize))
    }
    // let i = 0;
    // while (i < file.size) {
    //     let curChunk = i;
    //     i += chunkSize;
    //     if ((file.size - i) < (chunkSize / 2)) {
    //         chunks.push(file.slice(curChunk))
    //         return chunks;
    //     }
    //     chunks.push(file.slice(curChunk, i))
    // }
    return chunks;
}

/**
 * 计算二进制数据的 Hash 值
 * @param blob {Blob} 需要计算 Hash 值的二进制数据
 * @returns {Promise<unknown>}
 */
export const createBlobHash = (blob) => {
    return new Promise((resolve, reject) => {
        try {
            const reader = new FileReader();
            const spark = new SparkMD5();
            reader.onload = e => {
                spark.append(e.target.result);
                resolve({
                    hash: spark.end()
                })
            };
            reader.readAsArrayBuffer(blob);
        } catch (e) {
            console.error("createChunkHash error: ", e);
            reject(e);
        }
    });
}

/**
 *  计算文件指定片段的 Hash 值
 * @param file {File} 需要计算 Hash 值的文件
 * @param index {Number} 计算哪一段数据的 Hash 值
 * @param chunkSize  {Number} 需要计算 Hash 值的文件片段大小
 * @returns {Promise<unknown>}
 */
export const fileChunkHash = (file, index, chunkSize) => {
    return new Promise(async (resolve, reject) => {
        try {
            const start = index * chunkSize;
            const end = start + chunkSize;
            const blob = file.slice(start, end);
            const hash = await createBlobHash(blob);
            resolve({
                hash,
                start,
                end,
                blob
            });
        } catch (e) {
            reject(e);
        }
    });
}
/**
 * 带单位的文件大小
 * @param number {number}
 * @returns {string}
 */
export const fileSize = (number = 0) => {
    // if (number < 1) return '0 Byte';
    let unit = 'Byte';
    if (number >= 1024 ** 3) {
        number = number / 1024 ** 3;
        unit = 'GB';
    } else if (number >= 1024 ** 2) {
        number = number / 1024 ** 2;
        unit = 'MB';
    } else if (number >= 1024) {
        number = number / 1024;
        unit = 'KB';
    }
    number = number?.toFixed(2);
    return number + " " + unit;
};

/**
 * 获取扩展名
 * @param name
 * @returns {string | undefined}
 */
export const extname = (name) => {
    // return name?.match(/\..+$/)?.[0]?.toLowerCase();
    return name?.split('.').pop()?.toLowerCase();
};

/**
 * 解析图片的获取第一帧作为封面
 * @param fileVideo {File}
 * @returns {Promise<Blob>}
 */
export const parseVideo = (fileVideo) => {
    return new Promise((resolve, reject) => {
        let video = document.createElement("video");
        video.muted = true;
        video.setAttribute("src", URL.createObjectURL(fileVideo));
        video.setAttribute("autoplay", "autoplay");
        video.setAttribute("crossOrigin", "anonymous"); //设置跨域 否则toDataURL导出图片失败
        video.setAttribute("width", "400"); //设置大小，如果不设置，下面的canvas就要按需设置
        video.setAttribute("height", "300");
        video.currentTime = 7; //视频时长，一定要设置，不然大概率白屏
        video.addEventListener("loadeddata", function (e) {
            console.log("loadeddata e: ", e);
            console.log('视频时长：' + video.duration + '秒');
            let canvas = document.createElement("canvas"),
                width = video.width, //canvas的尺寸和图片一样
                height = video.height;
            canvas.width = width; //画布大小，默认为视频宽高
            canvas.height = height;
            let ctx = canvas.getContext("2d");
            if (!ctx) {
                return reject("无法捕获视频帧");
            }
            ctx.drawImage(video, 0, 0, width, height); //绘制canvas
            canvas.toBlob((e) => {
                // console.log("callback: ", e);
                resolve({blob:e,duration:video.duration});
                video.remove();
            }, "image/png"); //转换为base64
            // let info = {
            //     poster: dataURL,
            //     duration: parseInt(video.duration + ""),
            // };
            // return resolve(info);
        });
    });
}


/**
 *  取两个对象数组的交集 主要用于合并两个文件数组
 * @param a1 {Array}
 * @param a2 {Array}
 * @param field {String} 需要比较的字段
 */
export const beMixed = (a1, a2, field) => {
    const difference = a2.filter(item => !new Set(a1.map(item => item[field])).has(item[field]));
    a1.push(...difference);
}