<script setup>
import {ref, reactive} from 'vue'
import {Department, Users, addUser, showAll, updateUser,deleteUser} from '@/service/modules/backstage/student/student.js';
import {ElMessage, ElMessageBox} from 'element-plus'

// 定义相关变量和函数
const activeName = ref('department');
const departmentCategories = ref(null);
const dialogFormVisible = ref(false)
const xgdialogFormVisible = ref(false)
const formLabelWidth = '140px'
const users = ref([]);
const xgusers = ref([]);
const dname = ref('全部部门')
const zsl = ref(0)
const queryUser = reactive({
  id: 0,
  name: '',
  email: '',
  did: 0,
  ps: 1,
  pn: 10
});
const xgUserxx = reactive({
  id: 0
})
const scUserxx = reactive({
  id: 0
})
const defaultProps02 = {
  label: 'name', // 显示文本属性名，可根据实际情况修改
  value: 'id', // 值属性名，可根据实际情况修改
  children: 'ds' // 子节点属性名，可根据实际情况修改
}

const form = reactive({
  name: '',
  region: ''
})

const xgform = reactive({
  name: '',
  region: ''
})

// 在节点点击时处理逻辑
const handleNodeClick02 = (data) => {
  dname.value = data.name === '全部部门' ? '全部部门' : data.name
  if (activeName.value === 'department') {
    if (data.name === '全部部门') {
      queryUser.did = 0;
      getUsers(queryUser.did);
    } else {
      queryUser.did = data.id;
      getUsers(queryUser.did);
    }
    console.log('当前选中的部门节点为：', data)
    // 处理部门节点的逻辑
  }
}

// 添加全部分类节点
const addAllCategoryNode02 = (data) => {
  data.unshift({
    name: '全部部门',
    rcs: []
  })
}

const transformDataToOptions = (data) => {
  return data.map(item => {
    const option = {
      value: item.id,
      label: item.name
    }
    if (item[defaultProps02.children]) {
      option.children = transformDataToOptions(item[defaultProps02.children])
    }
    return option
  })
}

// 获取部门数据
const getDepartments = async () => {
  try {
    const result = await Department();
    const data = result.data;
    options.value = transformDataToOptions(data);
    addAllCategoryNode02(data); // 添加全部分类节点
    console.log("options: ", options)
    departmentCategories.value = data;
    console.log(result);
  } catch (error) {
    console.error('获取部门失败：', error);
  }
};

// 获取学员数据
const getUsers = async () => {
  try {
    const result = await Users(queryUser);
    users.value = result.data.list;
    zsl.value = result.data.list.length;
    console.log(result);
  } catch (error) {
    console.error('获取学员失败：', error);
  }
}

const getResetting = async () => {
  try {
    queryUser.name = "";
    queryUser.email = "";
    if (queryUser.did === "") {
      queryUser.did = 0;
    }
    const result = await Users(queryUser);
    users.value = result.data.list;
    console.log(result);
  } catch (error) {
    console.error('获取学员失败：', error);
  }
}

// 格式化日期时间
function formatDate(dateTimeString) {
  const dateTime = new Date(dateTimeString);
  const year = dateTime.getFullYear();
  const month = String(dateTime.getMonth() + 1).padStart(2, '0');
  const day = String(dateTime.getDate()).padStart(2, '0');
  const hours = String(dateTime.getHours()).padStart(2, '0');
  const minutes = String(dateTime.getMinutes()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}`;
}

const curPageStu = ref(1);
const pageSize = ref(10);
// 调用接口获取部门和学员数据
getDepartments();
getUsers(queryUser.did);

function handleSizeChange(pageSize) {

}

function handleCurrentChange(pageNum) {

}

//学员头像
const headSculpture = ref('http://demo-minio.playedu.xyz/playedu/images/4uQtpgwn2OxIv0Sr1YgTFEGxIxFpsU9v.png');
//学员姓名
const fullName = ref('');
//登录邮箱
const LoginMailbox = ref('');
//登录密码
const LoginPassword = ref('');
//所属部门
const AffiliationDepartments = ref('');
//身份证号
const IDCard = ref('')

const props = {
  checkStrictly: true,
}

const handleChange = (value) => {
  console.log(value)
}

const options = ref([]);

const AddStudent = async () => {
  dialogFormVisible.value = true
}

const validateEmail = (email) => {
  const emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  return emailRegex.test(email);
};

//添加学员
const determine = async () => {
  try {
    // 构建学员信息对象
    const student = {
      email: LoginMailbox.value,
      name: fullName.value,
      avatar: headSculpture.value,
      password: LoginPassword.value,
      id_card: IDCard.value,
      dep_id: AffiliationDepartments.value[0]
    };

    if (student.email === '' || student.name === '' || student.id_card === '' || student.password === '' || student.dep_id === '') {
      ElMessage({
        type: 'error',
        message: '请填写完整信息',
      });
      return;
    }

    // 验证邮箱格式
    if (!validateEmail(student.email)) {
      ElMessage({
        type: 'error',
        message: '邮箱格式不正确',
      });
      return;
    }

    // 验证身份证号格式
    const idCardRegex = /^\d{17}[0-9xX]$/;
    if (!idCardRegex.test(student.id_card)) {
      ElMessage({
        type: 'error',
        message: '身份证号格式不正确',
      });
      return;
    }

    // 调用后端接口添加学员
    const response = await addUser(student);
    // 清空表单数据
    headSculpture.value = 'http://demo-minio.playedu.xyz/playedu/images/4uQtpgwn2OxIv0Sr1YgTFEGxIxFpsU9v.png';
    fullName.value = '';
    LoginMailbox.value = '';
    LoginPassword.value = '';
    AffiliationDepartments.value = [];
    IDCard.value = '';
    // 关闭对话框
    dialogFormVisible.value = false;

    // 刷新学员列表等相关数据
    getUsers();
    ElMessage({
      type: 'success',
      message: '添加学员成功',
    });
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '添加学员失败',
    });
  }
}

//学员头像
const xgheadSculpture = ref('http://demo-minio.playedu.xyz/playedu/images/4uQtpgwn2OxIv0Sr1YgTFEGxIxFpsU9v.png');
//学员姓名
const xgfullName = ref('');
//登录邮箱
const xgLoginMailbox = ref('');
//登录密码
const xgLoginPassword = ref('');
//所属部门
const xgAffiliationDepartments = ref('');
//身份证号
const xgIDCard = ref('')

//修改学员
const handleStudents = async (id) => {
  xgUserxx.id = id;
  xgdialogFormVisible.value = true
  const xyxx = await showAll(xgUserxx);
  xgusers.value = xyxx.data.value;
  xgheadSculpture.value = xyxx.data[0].avatar;
  xgfullName.value = xyxx.data[0].name;
  xgLoginMailbox.value = xyxx.data[0].email;
  xgLoginPassword.value = xyxx.data[0].password;
  xgAffiliationDepartments.value = [xyxx.data[0].userDepartment[0].department[0].id];
  xgIDCard.value = xyxx.data[0].idCard;
}

//修改学员
const xgdetermine = async () => {
  try {
    // 构建学员信息对象
    const student = {
      id: xgUserxx.id,
      email: xgLoginMailbox.value,
      name: xgfullName.value,
      avatar: xgheadSculpture.value,
      password: xgLoginPassword.value,
      id_card: xgIDCard.value,
      dep_id: xgAffiliationDepartments.value[0]
    };

    if (student.email === '' || student.name === '' || student.id_card === '' || student.password === '' || student.dep_id === '') {
      ElMessage({
        type: 'error',
        message: '请填写完整信息',
      });
      return;
    }

    // 验证邮箱格式
    if (!validateEmail(student.email)) {
      ElMessage({
        type: 'error',
        message: '邮箱格式不正确',
      });
      return;
    }

    // 验证身份证号格式
    const idCardRegex = /^\d{17}[0-9xX]$/;
    if (!idCardRegex.test(student.id_card)) {
      ElMessage({
        type: 'error',
        message: '身份证号格式不正确',
      });
      return;
    }

    // 调用后端接口添加学员
    const response = await updateUser(student);
    // 清空表单数据
    headSculpture.value = 'http://demo-minio.playedu.xyz/playedu/images/4uQtpgwn2OxIv0Sr1YgTFEGxIxFpsU9v.png';
    fullName.value = '';
    LoginMailbox.value = '';
    LoginPassword.value = '';
    AffiliationDepartments.value = [];
    IDCard.value = '';
    // 关闭对话框
    xgdialogFormVisible.value = false;
    // 刷新学员列表等相关数据
    getUsers();
    // 提示修改成功
    ElMessage({
      type: 'success',
      message: '修改学员成功',
    });
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '修改学员失败',
    });
  }
}

//删除学员
const handleMore = async (id) => {
  scUserxx.id = id;
  ElMessageBox.confirm(
      '确认删除此学员？',
      '操作确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: '操作确认',
      }
  )
      .then(() => {
        deleteUser(scUserxx)
            .then(() => {
              ElMessage({
                type: 'success',
                message: '删除成功',
              });
              // 删除成功后刷新学员列表等相关数据
              getUsers();
            })
            .catch(() => {
              ElMessage({
                type: 'error',
                message: '删除失败',
              });
            });
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '删除失败',
        })
      })
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside style="width: 200px;margin: 10px;padding-top: 10px;border-right: 1px solid #f6f6f6;">
        <!-- 在 el-tree 组件上添加 class-name 属性，设置自定义的 CSS 类名 -->
        <el-tree v-if="departmentCategories" :data="departmentCategories"
                 :props="defaultProps02" :node-key="defaultProps02.label"
                 class-name="my-node" @node-click="handleNodeClick02"/>
      </el-aside>

      <el-main>
        <div class="d01">
          <div class="d02" style="display: flex;font-size: 16px;padding-bottom: 20px;">
            <p>学员 | {{ dname }}</p>
          </div>

          <div style="display: flex;align-items: center;justify-content: space-between;padding-bottom: 20px;">
            <el-button class="btjxy01" @click="AddStudent">
              <span>
                <svg class="svg01" viewBox="64 64 896 896" focusable="false" data-icon="plus"
                     width="1em" height="1em" fill="currentColor" aria-hidden="true">
                  <path d="M482 152h60q8 0 8 8v704q0 8-8 8h-60q-8 0-8-8V160q0-8 8-8z"></path>
                  <path d="M176 474h672q8 0 8 8v60q0 8-8 8H176q-8 0-8-8v-60q0-8 8-8z"></path>
                </svg>
              </span>
              <span style="padding-left: 5px">添加学员</span>
            </el-button>

            <div style="display: flex">
              <div style="display: flex;align-items: baseline;padding-right: 15px">
                <div style="width: 55px">姓名:</div>
                <el-input v-model="queryUser.name" placeholder="请输入姓名关键字" style="width: 160px;"
                          clearable></el-input>
              </div>
              <div style="display: flex;align-items: baseline;padding-right: 15px">
                <div style="width: 55px">邮箱:</div>
                <el-input v-model="queryUser.email" placeholder="请输入邮箱账号" style="width: 160px;"
                          clearable></el-input>
              </div>
              <div>
                <el-button class="bcz01" @click="getResetting()">重置</el-button>
                <el-button class="bcz02" @click="getUsers()">查询</el-button>
              </div>
            </div>
          </div>

          <div class="d04" style="margin-bottom: 30px">
            <el-table style="width: 100%" :data="users">
              <el-table-column label="学员">
                <template #default="scope">
                  <div class="course-name" style="display:flex; align-items: center;">
                    <div style="display: flex; align-items: center ;">
                      <el-image style=" border-radius:5px; width:40px; height:40px;" :src="scope.row.avatar"></el-image>
                    </div>
                    <span style="margin-left: 10px">{{ scope.row.name }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="所属部门">
                <template #default="scope">
                  <span>{{ scope.row.userDepartment[0].department[0].name }}</span>
                </template>
              </el-table-column>
              <el-table-column label="登录邮箱">
                <template #default="scope">
                  <span>{{ scope.row.email }}</span>
                </template>
              </el-table-column>
              <el-table-column label="加入时间">
                <template #default="scope">
                  <span>{{ formatDate(scope.row.createdAt) }}</span>
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
                :total="zsl"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </el-main>
    </el-container>
  </div>

  <div class="tjxy">
    <el-dialog v-model="dialogFormVisible" title="添加学员" style="width: 450px;border-radius: 10px;">
      <el-form :model="form">
        <el-form-item label="学员头像：" :label-width="formLabelWidth">
          <el-image style=" border-radius:5px; width:40px; height:40px;" :src="headSculpture"></el-image>
          <el-button style="margin-left: 30px">更换头像</el-button>
        </el-form-item>
        <el-form-item label="学员姓名：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="fullName" placeholder="请填写学员姓名" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="登录邮箱：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="LoginMailbox" placeholder="请输入学员登录邮箱" autocomplete="off"
                    clearable/>
        </el-form-item>
        <el-form-item label="登录密码：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="LoginPassword" type="password" placeholder="请输入登录密码"
                    autocomplete="off" clearable show-password/>
        </el-form-item>
        <el-form-item label="所属部门：" :label-width="formLabelWidth">
          <el-cascader style="width: 250px;" v-model="AffiliationDepartments" placeholder="请选择学员所属部门"
                       :options="options" :props="props" :show-all-levels="false" clearable/>
        </el-form-item>
        <el-form-item label="身份证号：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="IDCard" placeholder="请填写学员身份证号" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="determine">
          确定
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>

  <div class="xgxy">
    <el-dialog v-model="xgdialogFormVisible" title="修改学员" style="width: 450px;border-radius: 10px;">
      <el-form :model="xgform">
        <el-form-item label="学员头像：" :label-width="formLabelWidth">
          <el-image style=" border-radius:5px; width:40px; height:40px;" :src="xgheadSculpture"></el-image>
          <el-button style="margin-left: 30px">更换头像</el-button>
        </el-form-item>
        <el-form-item label="学员姓名：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="xgfullName" placeholder="请填写学员姓名" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="登录邮箱：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="xgLoginMailbox" placeholder="请输入学员登录邮箱" autocomplete="off"
                    clearable/>
        </el-form-item>
        <el-form-item label="登录密码：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="xgLoginPassword" type="password" placeholder="请输入登录密码"
                    autocomplete="off" clearable show-password/>
        </el-form-item>
        <el-form-item label="所属部门：" :label-width="formLabelWidth">
          <el-cascader style="width: 250px;" v-model="xgAffiliationDepartments" placeholder="请选择学员所属部门"
                       :options="options" :props="props" :show-all-levels="false" clearable/>
        </el-form-item>
        <el-form-item label="身份证号：" :label-width="formLabelWidth">
          <el-input style="width: 250px;" v-model="xgIDCard" placeholder="请填写学员身份证号" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="xgdialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="xgdetermine">
          确定
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.tjxy >>> .el-dialog__body {
  padding: 10px;
}

.common-layout {
  background-color: #ffffff;
  border-radius: 10px;
}

>>> .el-table .el-table__cell {
  padding: 20px 0px;
}

>>> .el-tree .el-tree-node .el-tree-node__content {
  height: 50px;
  line-height: 50px;
  font-size: 14px;
}

>>> .el-tree > .el-tree-node > .el-tree-node__content > .el-icon {
  padding-left: 10px;
}

.btjxy01 {
  background-color: #ff4d4f;
  font-size: 14px;
  color: #ffffff;
  border-radius: 6px;
}

.btjxy01:hover {
  background-color: #f37d7e;
}

.bcz01 {
  font-size: 13px;
  background-color: #ffffff;
  border-color: #d9d9d9;
}

.bcz01:hover {
  border-color: #ff4d4f;
  color: #ff4d4f;
}

.bcz02 {
  font-size: 13px;
  background-color: #ff4d4f;
  border-color: #d9d9d9;
  color: #fff;
}

.bcz02:hover {
  background-color: #f37d7e;
}
</style>