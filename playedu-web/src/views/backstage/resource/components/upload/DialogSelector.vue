<script setup>
import {Delete} from "@element-plus/icons-vue";
import FileSelector from "@views/backstage/resource/components/upload/FileSelector.vue";
import {ref, useSlots} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {beMixed, createFileChunks, fileHash, fileSize, parseVideo} from "@/utils/FileUtil.js";

/**
 * 获取 FileSelector Dom 对象
 */
const fileSelectorEl = ref(null);
/**
 *  @description 文件上传组件的属性
 */
const props = defineProps({
    showDialog: {
        type: Boolean,
        default: () => false
    },
    title: {
        type: String,
        default: () => "上传文件"
    },
    exts: {
        type: Array,
        default: () => [],
    },
    maxSize: {
        type: Number,
        default: () => 1024 * 1024 * 1024 * 5 // 默认为 5GB
    },
    maxLength: {
        type: Number,
        default: () => 10
    }
})

/**
 * 选择文件列表
 */
const fileList = ref([]);

/**
 *  @description 获取父组件传递的插槽
 */
const slots = useSlots();
/**
 *  获取定义事件
 */
const emits = defineEmits(['closeSelector', "processSelectedFiles"]);
/**
 * 点击关闭，后提示用户是否确认关闭
 */
const closeDialog = () => {
    if (!fileList.value || fileList.value.length <= 0) {
        emits("closeSelector", false)
        return;
    }
    ElMessageBox.confirm(
        '是否确认取消上传文件',
        '警告',
        {
            confirmButtonText: '确认关闭',
            cancelButtonText: '取消关闭',
            type: '警告',
        }
    )
        .then(() => {
            emits("closeSelector", false)
            ElMessage({
                type: 'info',
                message: '上传已取消！',
            })
        })
        .catch((e) => {
            // console.log("Cancel Upload Event: ", e);
            // 执行上传逻辑
            ElMessage({
                type: 'info',
                message: '未关闭',
            })
        })
}

/**
 * 测试函数
 */
const testFileUtil = async (event) => {
    console.log("Upload Submit Event: ", event)
    let chunkSize = 1024 * 1024;
    let chunks = createFileChunks(event.file, chunkSize);
    console.log("Chunks: ", chunks);
    console.log("testFileUtil: ", await fileHash(chunks));
}
const closeHandler = () => {
    console.log("FileList: ", fileSelectorEl.value.getFileList())
    fileList.value = [];
    fileSelectorEl.value.clearInput();
}
const openHandler = () => {
    console.log("FileList: ", fileSelectorEl.value.getFileList())
}
/**
 * FileSelector 调用
 * @param files
 */
const selectFile = async (files) => {
    if (files.length <= 0) {
        ElMessage({
            type: 'warning',
            message: '未选择文件',
        });
        return;
    } else if (files.length > props.maxLength) {
        ElMessage({
            type: 'warning',
            message: '文件数量太多，请压缩后在上传！'
        });
        return;
    }
    // console.log("selectFile: ", files[0])
    // let video = await parseVideo(files[0]);
    // console.log("parseVideo: ", video)
    if (fileList.value && fileList.value.length > 0) {
        beMixed(fileList.value, files, "name");
    } else {
        fileList.value.push(...files);
    }
    console.log("Add File fileList: ", fileList.value)
}

</script>

<template>
    <el-dialog :close-on-click-modal="!fileList || fileList.length <= 0" @close="closeHandler" @open="openHandler"
               :before-close="closeDialog"
               style="min-width: 320px;"
               width="36%" :model-value="props.showDialog"
               :title="props.title">
        <FileSelector ref="fileSelectorEl" :exts="props.exts" :max-size="props.maxSize" @selectFile="selectFile">
            <file-selector>
                <template v-for="(value, name) in slots" #[name]>
                    <slot :name="name"></slot>
                </template>
            </file-selector>
        </FileSelector>
        <!--  v-if="!fileList && fileList.length > 0" -->
        <el-table v-if="fileList && fileList.length > 0"
                  :row-class-name="({ rowIndex })=>rowIndex % 2 === 0 ? 'warning-row' : 'success-row'"
                  :data="fileList" max-height="300px" style="width: 100%">
            <el-table-column align="center" prop="fileName" label="文件名称" :formatter="row=>row.name"/>
            <el-table-column align="center" prop="fileSize" label="大小" :formatter="row=>fileSize(row.size)"/>
            <el-table-column align="center" fixed="right" label="操作" width="80">
                <template #default="scope">
                    <el-button type="danger" :icon="Delete" circle
                               @click.stop="()=>fileList = fileList.filter(e => e.name !== scope.row.name)"/>
                </template>
            </el-table-column>
        </el-table>
        <template #footer>
                <span class="dialog-footer">
                  <el-button @click="closeDialog">关闭</el-button>
                  <el-button type="danger" @click="emits('processSelectedFiles', {...fileList})">
                    确定
                  </el-button>
                </span>
        </template>
    </el-dialog>
</template>

<style scoped>

>>> .el-dialog__header {
    text-align: left;
}

>>> .el-dialog__body {
    padding: 10px 20px !important;
}

>>> .el-table__body tr.el-table__row {
    cursor: pointer;
}

>>> .el-table__body .warning-row {
    background: var(--el-color-warning-light-9);
}

>>> .el-table__body tr td,
>>> .el-table__body tr td {
    background: inherit;
}

>>> .el-table__body .success-row {
    background: var(--el-color-success-light-9);
}
</style>