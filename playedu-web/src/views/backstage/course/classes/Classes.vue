<script setup>
import {ref,reactive} from 'vue'
import {ResourceCategories, Department, Course} from '@/service/modules/backstage/course/Classes.js';

const activeName = ref('category')
const resourceCategories = ref(null)
const departmentCategories = ref(null)
const rcname = ref('全部分类')

const queryCourse = reactive({
  title: '',
  did: 0,
  rcid: 0,
  ps:1,
  pn:10
});

const defaultProps01 = {
  label: 'rc1name', // 显示文本属性名，可根据实际情况修改
  children: 'rcs' // 子节点属性名，可根据实际情况修改
}

const defaultProps02 = {
  label: 'name', // 显示文本属性名，可根据实际情况修改
  children: 'ds' // 子节点属性名，可根据实际情况修改
}

const handleNodeClick01 = (data) => {
  rcname.value = data.rc1name === '全部分类' ? '全部分类' : data.rc1name
  if (activeName.value === 'category') {
    if (data.name === '全部分类') {
      queryCourse.rcid = 0;
      queryCourse.did = 0;
      getCourses(queryCourse);
    } else {
      queryCourse.rcid = data.rc1id;
      queryCourse.did = 0;
      getCourses(queryCourse);
    }
    console.log('当前选中的分类节点为：', data)
    // 处理分类节点的逻辑
  }
}

const handleNodeClick02 = (data) => {
  rcname.value = data.name === '全部部门' ? '全部部门' : data.name
  if (activeName.value === 'department') {
    if (data.name === '全部部门') {
      queryCourse.did = 0;
      queryCourse.rcid = 0;
      getCourses(queryCourse);
    } else {
      queryCourse.did = data.id;
      queryCourse.rcid = 0;
      getCourses(queryCourse);
    }
    console.log('当前选中的部门节点为：', data)
    // 处理部门节点的逻辑
  }
}

const addAllCategoryNode01 = (data) => {
  data.unshift({
    rc1name: '全部分类',
    rcs: []
  })
}

const addAllCategoryNode02 = (data) => {
  data.unshift({
    name: '全部部门',
    rcs: []
  })
}

const getDepartments = async () => {
  try {
    const result = await Department();
    const data = result.data;
    addAllCategoryNode02(data); // 添加全部分类节点
    departmentCategories.value = data;
    console.log(result);
  } catch (error) {
    console.error('获取部门失败：', error);
  }
};

const getResourceCategories = async () => {
  try {
    const result = await ResourceCategories()
    const data = result.data
    addAllCategoryNode01(data) // 添加全部分类节点
    resourceCategories.value = data
    console.log(result)
  } catch (error) {
    console.error('获取资源分类失败：', error)
  }
}

getDepartments();
getResourceCategories();

const input = ref('')

const courses = ref([]);

const getCourses = async () => {
  try {
    const result = await Course(queryCourse); // 假设Course模块提供了getCourses方法来获取课程数据
    courses.value = result.data.list;
    console.log(result);
  } catch (error) {
    console.error('获取课程失败：', error);
  }
};

// 调用getCourses方法来获取课程数据
getCourses();

function formatDate(dateTimeString) {
  const dateTime = new Date(dateTimeString);
  const year = dateTime.getFullYear();
  const month = String(dateTime.getMonth() + 1).padStart(2, '0');
  const day = String(dateTime.getDate()).padStart(2, '0');
  const hours = String(dateTime.getHours()).padStart(2, '0');
  const minutes = String(dateTime.getMinutes()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}`;
}

import { ElMessageBox } from 'element-plus'
import {Users} from "@/service/modules/backstage/student/student.js";

const drawer2 = ref(false)
const direction = ref('rtl')
const radio1 = ref('Option 1')
function cancelClick() {
  drawer2.value = false
}
function confirmClick() {
  ElMessageBox.confirm(`Are you confirm to chose ${radio1.value} ?`)
      .then(() => {
        drawer2.value = false
      })
      .catch(() => {
        // catch error
      })
}

// do not use same name with ref
const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const onSubmit = () => {
  console.log('submit!')
}


const curPageStu = ref(1);
const pageSize = ref(10);

function handleSizeChange(pageSize) {

}

function handleCurrentChange(pageNum) {

}

const getResetting = async () => {
  try {
    queryCourse.title = "";
    const result = await Course(queryCourse);
    courses.value = result.data.list;
    console.log(result);
  } catch (error) {
    console.error('获取学员失败：', error);
  }
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="240px">
        <el-tabs v-model="activeName" class="demo-tabs" :stretch="true">
          <!-- 分类选项卡的内容 -->
          <el-tab-pane label="分类" name="category">
            <!-- 在 el-tree 组件上添加 class-name 属性，设置自定义的 CSS 类名 -->
            <el-tree v-if="resourceCategories" :data="resourceCategories"
                     :props="defaultProps01" :node-key="defaultProps01.label"
                     class-name="my-node" @node-click="handleNodeClick01"/>
          </el-tab-pane>
          <!-- 部门选项卡的内容 -->
          <el-tab-pane label="部门" name="department">
            <!-- 在 el-tree 组件上添加 class-name 属性，设置自定义的 CSS 类名 -->
            <el-tree v-if="departmentCategories" :data="departmentCategories"
                     :props="defaultProps02" :node-key="defaultProps02.label"
                     class-name="my-node" @node-click="handleNodeClick02"/>
          </el-tab-pane>
        </el-tabs>
      </el-aside>



      <el-main>
        <div class="d01">
          <div class="d02" style="font-size: 16px">
            <p>线上课 | {{ rcname }}</p>
          </div>
          <div class="d03">
            <el-button class="d03-1"  @click="drawer2 = true">
              <span>
                <svg class="svg01" viewBox="64 64 896 896" focusable="false" data-icon="plus"
                     width="1em" height="1em" fill="currentColor" aria-hidden="true">
                  <path d="M482 152h60q8 0 8 8v704q0 8-8 8h-60q-8 0-8-8V160q0-8 8-8z"></path>
                  <path d="M176 474h672q8 0 8 8v60q0 8-8 8H176q-8 0-8-8v-60q0-8 8-8z"></path>
                </svg>
              </span>
              <span style="padding-left: 5px">新建课程</span>
            </el-button>

            <el-drawer v-model="drawer2" :direction="direction" size="40%" >
              <template #header>
                <h4 style="font-size: 16px;color: #000000;padding-left: 20px">新建课程</h4>
              </template>
              <template #default>
                <el-form :model="form" label-width="120px">
                  <el-form-item label="课程分类：">
                    <el-input v-model="form.name" />
                  </el-form-item>
                  <el-form-item label="课程名称：">
                    <el-select v-model="form.region" placeholder="please select your zone">
                      <el-option label="Zone one" value="shanghai" />
                      <el-option label="Zone two" value="beijing" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="课程属性：">
                    <el-radio-group v-model="form.resource">
                      <el-radio label="必修课" />
                      <el-radio label="选修课" />
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="指派部门：">
                    <el-radio-group v-model="form.resource">
                      <el-radio label="全部部门" />
                      <el-radio label="选择部门" />
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="课程封面：">
                    <div>
                      <img style="width: 160px; height: 120px;border-radius:7px;" src="http://demo-minio.playedu.xyz/playedu/images/ZryMv2PZWPledBa3Yks9g7Tfgkn2U2nN.png"/>
                    </div>
                    <div>
                      <div></div>
                    </div>
                  </el-form-item>
                  <el-form-item label="课时列表：">
                    <el-radio-group v-model="form.resource">
                      <el-radio label="无章节" />
                      <el-radio label="有章节" />
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="课程简介：">
                    <el-input v-model="form.desc" type="textarea" />
                  </el-form-item>
                </el-form>
              </template>
              <template #footer>
                <div style="flex: auto">
                  <el-button @click="cancelClick"> 取 消 </el-button>
                  <el-button type="primary" @click="confirmClick"> 确 认 </el-button>
                </div>
              </template>
            </el-drawer>

            <div class="d04">
              <div class="d04-1">
                <div class="d04-1-1">课程名称：</div>
                <el-input v-model="queryCourse.title" class="d04-1-2" placeholder="请输入名称关键字" clearable/>
              </div>
              <div class="d04-2">
                <el-button class="d04-2-1" @click="getResetting()">重置</el-button>
                <el-button class="d04-2-2" @click="getCourses()">查询</el-button>
              </div>
            </div>
          </div>



          <div class="d05">
            <el-table style="width: 100%" :data="courses">
              <el-table-column prop="title" label="课程名称" width="300px">
                <template #default="scope">
                  <div class="course-name" style="display:flex; align-items: center;">
                    <div style="display: flex; align-items: center ;" >
                      <el-image style=" border-radius:5px; width:80px; height:60px;" :src="scope.row.thumb" ></el-image>
                    </div>
                    <span style="margin-left: 10px">{{ scope.row.title }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="课程分类">
                <template #default="scope">
                  <span>{{scope.row.resourceCourseCategories[0].resourceCategories[0].name }}</span>
                </template>
              </el-table-column>
              <el-table-column label="指派部门">
                <template #default="scope">
                  <span>{{ scope.row.courseDepartments[0].departments[0].name }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="required" label="必修/选修">
                <template #default="scope">
                  <span>{{ scope.row.required ? '必修课' : '选修课' }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="createdAt" label="创建时间">
                <template #default="scope">
                  <span>{{ formatDate(scope.row.createdAt)}}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button link type="primary" size="small" @click="handleStudents(scope.row.id)">编辑</el-button>
                  <span>|</span>
                  <el-button link type="primary" size="small" @click="handleMore(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="demo-pagination-block" style="display: flex;justify-content: flex-end;">
            <el-pagination
                v-model:current-page="curPageStu"
                v-model:page-size="pageSize"
                :page-sizes="[10, 20, 50, 100]"
                :background="true"
                layout="prev, pager, next, sizes"
                :total="3"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.common-layout {
  background-color: #ffffff;
  border-radius:10px;
}

.demo-tabs {
  height: 100%;
  padding-left: 20px;
  padding-top: 20px;
  padding-right: 20px;
  border-right: 1px solid #f6f6f6;
}

.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.my-node {
  font-size: 14px;
}

>>> .el-tree .el-tree-node .el-tree-node__content {
  height: 50px;
  line-height: 50px;
  font-size: 14px;
}

>>> .el-tree .el-icon {
  font-size: 16px;
}

.d01 > div {
  text-align: left;
  padding-bottom: 20px;
}

.d03, .d04 {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.d04-1 {
  display: flex;
  align-items: center;
  margin-right: 24px;
  font-size: 13px;
}

.d04-1-1 {
  width: 65px;
}

.d04-1-2 {
  width: 160px;
  height: 32px;
}

.d04-2-1 {
  font-size: 13px;
  background-color: #ffffff;
  border-color: #d9d9d9;
}

.d04-2-1:hover {
  color: #ff4d4f;
  border-color: #ff4d4f;
}

.d04-2-2:hover {
  background-color: #f37d7e;
}

.d04-2-2 {
  font-size: 13px;
  color: #fff;
  background-color: #ff4d4f;
  border-color: #d9d9d9;
}

.d03-1 {
  background-color: #ff4d4f;
  font-size: 15px;
  color: #ffffff;
  border-radius:6px;
}

.d03-1:hover {
  background-color: #f37d7e;
}

>>> .el-table__cell {
  color: #070707;
  font-size: 13px;
  border-bottom: 1px solid #f0f0f0;
}

>>>.el-drawer__header{
  border-bottom: 1px solid rgba(5, 5, 5, 0.06);
  margin-bottom: 0px;
  padding-bottom: 20px;
}

>>>.el-table .cell{
  padding:5px 12px;
}
</style>