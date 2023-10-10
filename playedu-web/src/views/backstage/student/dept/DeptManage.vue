<script setup>
import {ref} from 'vue'
import TangIcon from "@components/TangIcon.vue";
import {Department} from '@/service/modules/backstage/course/Classes.js';

const resourceDepartment = ref(null)

const defaultProps = {
  label: 'name', // 显示文本属性名，可根据实际情况修改
  children: 'ds' // 子节点属性名，可根据实际情况修改
}

const addAllDepartment = (data) => {
  ds: []
}


const getDepartment = async () => {
  try {
    const result = await Department()
    const data = result.data
    addAllDepartment(data) // 添加全部分类节点
    resourceDepartment.value = data
    console.log(result)
  } catch (error) {
    console.error('获取部门失败：', error)
  }
}

getDepartment();
</script>

<template>
  <div style="background-color: #ffffff;border-radius: 10px;margin-bottom: 20px;padding: 24px 36px;display: flex;">
    <el-button class="btjxy01">
              <span>
                <svg class="svg01" viewBox="64 64 896 896" focusable="false" data-icon="plus"
                     width="1em" height="1em" fill="currentColor" aria-hidden="true">
                  <path d="M482 152h60q8 0 8 8v704q0 8-8 8h-60q-8 0-8-8V160q0-8 8-8z"></path>
                  <path d="M176 474h672q8 0 8 8v60q0 8-8 8H176q-8 0-8-8v-60q0-8 8-8z"></path>
                </svg>
              </span>
      <span style="padding-left: 5px">新建分类</span>
    </el-button>
  </div>
  <div style="background-color: #ffffff;border-radius: 10px;height: 100%;display: flex;padding: 24px;">
    <el-tree
        :data="resourceDepartment"
        draggable
        :props="defaultProps"
        :node-key="defaultProps.label"
        default-expand-all
        node-key="id"
    >
      <template #default="{ node, data, store }">
        <span style="display: flex;justify-content: space-between;width: 100%">
          <div>
            {{ node.label }}
          </div>
          <div>
            <TangIcon class-name="icon-drag"/>
            <TangIcon class-name="icon-edit"/>
            <TangIcon class-name="icon-delete"/>
          </div>
        </span>
      </template>
    </el-tree>
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
</style>