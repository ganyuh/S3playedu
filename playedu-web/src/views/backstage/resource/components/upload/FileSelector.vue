<script setup>
import {defineEmits, ref, useAttrs} from "vue";
import {UploadFilled} from '@element-plus/icons-vue'
import {extname, parseVideo} from "@/utils/FileUtil.js";

/**
 *  @description 文件上传组件的属性
 */
const props = defineProps({
    exts: {
        type: Array,
        default: () => [],
    },
    maxSize: {
        type: Number,
        default: () => 1024 * 1024 * 1024 * 5 // 默认为 5GB
    }
})
const emits = defineEmits(['selectFile']);
/**
 * input Dom
 */
const inputEl = ref(null);
const form = ref(null);
/**
 * 鼠标是否处于拖拽状态
 */
const isDraging = ref(false);

/**
 *  @description 获取当前组件未定义的属性
 */
const attrs = useAttrs();
/**
 * 每一次选择文件时触发，将其添加到 fileList
 */
const changeFile = async (event, uploadFiles) => {
    console.log("isDirectory: ", event.raw.isDirectory);
    // if (event.raw instanceof File) {
    //     ElMessage({
    //         message: '暂时不支持文件夹！',
    //         type: 'warning',
    //     })
    //     return;
    // }
    console.log("changeFile event: ", event)
    console.log("changeFile uploadFiles: ", uploadFiles)
    let video = await parseVideo(event.raw);
    console.log("parseVideo: ", video)
}

/**
 * 验证文件名称是否合法
 * @param name
 * @returns {boolean}
 */
const validExt = (name) => (!props.exts||props.exts.length <=0)||props.exts.includes(extname(name));
/**
 * 验证文件大小是否合法
 * @param size
 * @returns {boolean}
 */
const validSize = (size) => size <= props.maxSize;
const dragInHandler = (e) => {
    if (!e.dataTransfer.types.includes('Files')) {
        return;
    }
    e.preventDefault();
    isDraging.value = true;
};
const dragLeaveHandler = (e) => {
    e.preventDefault();
    isDraging.value = false;
};
const dropHandler = async (e) => {
    e.preventDefault();
    isDraging.value = false;
    let results = await Promise.all(
        [...e.dataTransfer.items].map((item) =>
            handleEntry(item.webkitGetAsEntry())
        )
    );
    console.log("results: ", results)
    emits('selectFile', results
        .flat(Infinity)
        .filter((f) => (validExt(f.name) && validSize(f.size))))
};
const handleEntry = (entry) => {
    return new Promise((resolve) => {
        if (entry.isFile) {
            entry.file(resolve);
            return;
        }
        const dirReader = entry.createReader();
        // {dir: entry.name, subFile: await Promise.all(entries.map(handleEntry))}
        dirReader.readEntries(async (entries) => {
            resolve(await Promise.all(entries.map(handleEntry)));
        });
    });
};
const selectFile = (e) => {
    emits('selectFile', [...e.target.files].flat(Infinity)
        .filter((f) => (validExt(f.name) && validSize(f.size))));
    clearInput();
}
const clearInput = () => {
    console.log("clearInput 执行")
    form.value.reset();
}
const getFileList = () => {
    return inputEl.value.files;
}
defineExpose({
    clearInput,
    getFileList
});
</script>

<template>
    <div class="upload-selector"
         :class="{draging:isDraging}"
         @dragenter="dragInHandler"
         @dragover="dragInHandler"
         @drop="dropHandler"
         @dragleave="dragLeaveHandler"
         @click="inputEl.click()"
    >
        <div class="upload-dragger">
            <el-icon class="el-icon--upload">
                <upload-filled/>
            </el-icon>
            <slot name="upload-text">
                拖入文件或文件夹，点击上传
            </slot>
        </div>
        <form ref="form">
            <input ref="inputEl" @change="selectFile" style="display: none" type="file"
                   :accept="props.exts?.map(e=>e.replace(/^\.?/, '.')).join(',')"
                   multiple/>
            <!-- 选择文件夹：[webkitdirectory mozdirectory odirectory] -->
        </form>
    </div>
</template>

<style scoped>
.upload-selector {
    cursor: pointer;
    transition: border-color 0.3s;
    border: 1px dashed var(--el-border-color);
    border-radius: 7px;
    background: #fafafa;
}

.upload-selector,
.upload-selector * {
    transition: border-color, color 0.3s;
}

.upload-selector:hover,
.upload-selector:hover * {
    color: #ff4d4f !important;
    border-color: #ff4d4f;
}

.upload-selector.draging {
    border-color: #ff4d4f;
    border-width: 2px;
}

.upload-dragger {
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.el-icon--upload {
    font-size: 67px;
    color: var(--el-text-color-placeholder);
    margin-bottom: 16px;
    line-height: 50px;
}
</style>