<script setup>
import {ref, reactive} from 'vue'
import TangIcon from "@components/TangIcon.vue";
import { ResourceCategories, Add, Update, Delete, Getrc} from '@/service/modules/backstage/classification/classification.js';
import {ElMessage} from 'element-plus'

const resourceCategories = ref(null)
const dialogFormVisible = ref(false)
const dialogModify = ref(false)
const centerDialogVisible = ref(false)
const items = ref(0);
const names = ref('');
let scname = ref('');
let ids = ref(0);

const rc = reactive({
  parentId: 0,
  name: ''
})

const uprc = reactive({
  id: 0,
  name: '',
  parentId: 0
})

const defaultProps = {
  label: 'rc1name', // 显示文本属性名，可根据实际情况修改
  value: 'rc1id', // 值属性名，可根据实际情况修改
  children: 'rcs' // 子节点属性名，可根据实际情况修改
}

const addAllResourceCategories = (data) => {
}

const addAllCategories = (data) => {
  data.unshift({
    rc1id: 0,
    rc1name: '作为一级分类',
    rcs: []
  })
}

const transformDataToOptions = (data) => {
  return data.map(item => {
    const option = {
      value: item.rc1id,
      label: item.rc1name
    }

    if (item[defaultProps.children]) {
      option.children = transformDataToOptions(item[defaultProps.children])
    }

    return option
  })
}

const options = ref([]);

const props = {
  checkStrictly: true,
}

const getCategories = async () => {
  try {
    const result = await ResourceCategories()
    const data = result.data
    addAllCategories(data) // 添加全部分类节点
    options.value = transformDataToOptions(data)
    console.log("options: ", options)
  } catch (error) {
    console.error('获取资源分类失败：', error)
  }
}

const getResourceCategories = async () => {
  try {
    const result = await ResourceCategories()
    const data = result.data
    addAllResourceCategories(data) // 添加全部分类节点
    resourceCategories.value = data
    console.log(result)
  } catch (error) {
    console.error('获取资源分类失败：', error)
  }
}

const formLabelWidth = '140px'

getCategories();
getResourceCategories();

const form = reactive({
  rc1id: '',
  rc1name: '',
  rcs: []
})

const form01 = reactive({
  rc1id: '',
  rc1name: '',
  rc1parentId: '',
  rcs: []
})

const handleCascaderChange = (value) => {
  for (let i = 0; i < value.length; i++) {
    if (i === 2) {
      continue;
    }
    items.value = value[i];
  }
  console.log(items.value);
};

const increase = async () => {
  try {
    if (form.rc1id[0] === '' && form.rc1id[0] === null && form.rc1id[0] === undefined) {
      alert('所属上级不能为空');
      return;
    }
    if (!form.rc1name) {
      alert('分类名称不能为空');
      return;
    }
    rc.parentId = items.value;
    rc.name = form.rc1name;
    await Add(rc)
    form.rc1id = [0]
    form.rc1name = ''
    dialogFormVisible.value = false
    getCategories();
    getResourceCategories();
    ElMessage({
      type: 'success',
      message: '添加分类成功',
    });
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '添加分类失败',
    });
  }
}

const dialogModifys = async (row) => {
  let rc1id = 0;
  let rc1name = '';
  let rc1parentId = 0;

  if (row.level === 3) {
    // 获取当前选择的所属上级和分类名称
    rc1id = row.data.rc1parentId;
    rc1name = row.data.rc1name;
    rc1parentId = row.parent.data.rc1parentId;
    // 将所属上级和分类名称赋值给表单数据
    form01.rc1id = [rc1parentId, rc1id]
    form01.rc1name = rc1name;
  }
  if (row.level === 2) {
    // 获取当前选择的所属上级和分类名称
    rc1id = row.data.rc1parentId;
    rc1name = row.data.rc1name;
    // 将所属上级和分类名称赋值给表单数据
    form01.rc1id = [rc1id]
    form01.rc1name = rc1name;
  }
  if (row.level === 1) {
    // 获取当前选择的所属上级和分类名称
    rc1name = row.data.rc1name;
    // 将所属上级和分类名称赋值给表单数据
    form01.rc1id = [0]
    form01.rc1name = rc1name;
  }
  ids = row.data.rc1id;
  dialogModify.value = true
}

const dialogFormVisibles = async () => {
  form.rc1id = [0];
  dialogFormVisible.value = true
}

const modify = async () => {
  try {
    uprc.id = ids;
    console.log(uprc.id)
    uprc.name = form01.rc1name;
    uprc.parentId = form01.rc1id[form01.rc1id.length - 1];
    form01.rc1id = [0];
    form01.rc1name = '';
    await Update(uprc)
    dialogModify.value = false
    getCategories();
    getResourceCategories();
    ElMessage({
      type: 'success',
      message: '修改分类成功',
    });
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '修改分类失败',
    });
  }
}

const deletes = async (row) => {
  try {
    const response = await Getrc(row.rc1id);
    const data = response.data;
    if (row.rcs.length > 0) {
      scname.value = "存在子分类";
      centerDialogVisible.value = true
      return;
    }
    if (data!==0){
      scname.value = "存在外键";
      centerDialogVisible.value = true
      return;
    }
    const dele = await Delete(row.rc1id)

    //删除成功后，刷新分类数据
    getCategories();
    getResourceCategories();
    ElMessage({
      type: 'success',
      message: '删除节点成功',
    });
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '删除节点失败',
    });
  }
}
</script>

<template>
  <div style="background-color: #ffffff;border-radius: 10px;margin-bottom: 20px;padding: 24px 36px;display: flex;">`
    <el-button class="btjxy01" @click="dialogFormVisibles">
              <span>
                <svg class="svg01" viewBox="64 64 896 896" focusable="false" data-icon="plus"
                     width="1em" height="1em" fill="currentColor" aria-hidden="true">
                  <path d="M482 152h60q8 0 8 8v704q0 8-8 8h-60q-8 0-8-8V160q0-8 8-8z"></path>
                  <path d="M176 474h672q8 0 8 8v60q0 8-8 8H176q-8 0-8-8v-60q0-8 8-8z"></path>
                </svg>
              </span>
      <span style="padding-left: 5px">新建分类</span>
    </el-button>

    <el-dialog v-model="dialogFormVisible" title="新建分类" style="width: 420px;height: 240px;border-radius: 10px;">
      <el-form :model="form">
        <el-form-item label="所属上级：" :label-width="formLabelWidth">
          <el-cascader v-model="form.rc1id" @change="handleCascaderChange" :show-all-levels="false" :options="options"
                       :props="props" clearable/>
        </el-form-item>
        <el-form-item label="分类名称：" :label-width="formLabelWidth">
          <el-input v-model="form.rc1name" autocomplete="off" placeholder="请输入分类名称"
                    style="padding-right: 40px;font-size: 14px"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button class="qx" @click="dialogFormVisible = false">取 消</el-button>
        <el-button class="qd" @click="increase">确 定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>

  <div style="background-color: #ffffff;border-radius: 10px;height: 100%;display: flex;padding: 24px;">
    <el-tree
        :data="resourceCategories"
        draggable
        :props="defaultProps"
        :node-key="defaultProps.label"
        default-expand-all
    >
      <template #default="{ node, data, store }">
        <span style="display: flex;justify-content: space-between;width: 100%">
          <div>
            {{ node.label }}
          </div>
          <div>
            <TangIcon class-name="icon-drag"/>
            <TangIcon class-name="icon-edit" @click="dialogModifys(node)"/>
            <TangIcon class-name="icon-delete" @click="deletes(node.data)"/>
          </div>
        </span>
      </template>
    </el-tree>
  </div>

  <div class="scfl">
    <el-dialog
        v-model="centerDialogVisible"
        title="操作确认"
        width="30%"
        align-center
    >
      <span style="padding-left: 30px">原因为{{scname}}，无法删除，请先解除关联再删除！</span>
      <template #footer>
                <span class="dialog-footer">
                  <el-button type="primary" @click="centerDialogVisible = false">
                    好的
                  </el-button>
                </span>
      </template>
    </el-dialog>
  </div>

  <div class="xgfl">
    <el-dialog v-model="dialogModify" title="修改分类"
               style="width: 420px;height: 240px;border-radius: 10px;">
      <el-form :model="form01">
        <el-form-item label="所属上级：" :label-width="formLabelWidth">
          <el-cascader v-model="form01.rc1id" @change="handleCascaderChange" :show-all-levels="false"
                       :options="options" :props="props" clearable/>
        </el-form-item>
        <el-form-item label="部门名称：" :label-width="formLabelWidth">
          <el-input v-model="form01.rc1name" autocomplete="off" placeholder="请输入部门名称"
                    style="padding-right: 40px;font-size: 14px"/>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                  <el-button class="qx" @click="dialogModify = false">取 消</el-button>
                  <el-button class="qd" @click="modify">确 定</el-button>
                </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.btjxy01 {
  background-color: #ff4d4f;
  font-size: 14px;
  color: #ffffff;
  border-radius: 6px;
}

.btjxy01:hover {
  background-color: #f37d7e;
}

>>> .el-tree {
  width: 350px;
}

>>> .el-tree .el-tree-node .el-tree-node__content {
  height: 50px;
  line-height: 50px;
  font-size: 14px;
}

>>> .el-tree .el-tree-node .el-tree-node__content .el-icon {
  font-size: 18px;
}

>>> .iconfont {
  font-size: 24px;
  color: #0003;
}

>>> .el-dialog__body {
  padding: 10px;
}

.xgfl >>> .el-dialog__body {
  padding: 10px;
}

.qx:hover {
  color: #ff4d4f;
  border-color: #ff4d4f;
  background-color: #fff;
}

.qd {
  color: #fff;
  background-color: #ff4d4f;
  border-color: #ff4d4f;
}

.qd:hover {
  background-color: #ff7875;
}
</style>
