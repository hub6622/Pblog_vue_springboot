<template>
  <div style="height:100%;min-height: 1000px;width: 1000px; margin: 0 auto;">
    <el-card style="max-width: 800px;margin: 0 auto;">
      <template #header>
        <div class="card-header">
          <span style="font-size: 29px">用户信息</span>
        </div>
      </template>
      <el-row>
        <el-col :span="12">
          <el-form
              :label-position="'top'"
              ref="ruleFormRef"
              style="max-width: 300px"
              :model="userInfo"
              :rules="rules"
              label-width="auto"
              class="demo-ruleForm"
              :size="formSize"
              status-icon
              autocomplete="off"
          >
            <el-form-item prop="name">
              <span>用户名:(用户名唯一不可修改)</span>
              <el-input placeholder="用户名" disabled v-model="userInfo.name"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <span>原密码:</span>
              <el-input type="password" v-model="userInfo.password" autocomplete="off" placeholder="请输入原密码"/>
            </el-form-item>
            <el-form-item prop="newPassword">
              <span>新密码:</span>
              <el-input type="password" v-model="userInfo.newPassword" autocomplete="off" placeholder="请输入新密码"/>
            </el-form-item>
            <el-form-item prop="reNewPassword">
              <span>确认新密码:</span>
              <el-input type="password" v-model="userInfo.reNewPassword" autocomplete="off" placeholder="确认新密码"/>
            </el-form-item>
            <el-form-item prop="email">
              <span>邮箱:</span>
              <el-input type="text" placeholder="邮箱" v-model="userInfo.email"/>
            </el-form-item>
            <el-form-item>
              <el-button style="margin-left: 80px;" type="primary" @click="changeUserInfo">
                修改
              </el-button>
              <el-button @click="resetForm(ruleFormRef)">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-avatar :size="350" shape="square" :src="imgUrl"/>
          <el-upload
              ref="uploadRef"
              :show-file-list="false"
              :auto-upload="true"
              action="/api/upload"
              name="file"
              :headers="{'Authorization':token}"
              :on-success="uploadSuccess"
          >
            <el-button class="mid-btn" type="success">
              选择头像
            </el-button>
          </el-upload>
          <el-button class="mid-btn" type="primary" @click="changeUserAvatar">
            上传头像
          </el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>


</template>

<script setup lang="ts">
import {useTokenStore} from "@/stores/token"
import {useUserInfoStore} from "@/stores/userInfo"
import {updateUserInfoService, updateAvatarService, userInfoService, confirmPasswordService} from "@/api/user"
import {reactive, ref} from "vue";
import {type ComponentSize, ElMessage, FormInstance, type FormRules} from "element-plus";

interface UserInfo {
  id: number;
  name: string;
  password: string;
  email: string;
  avatar: string;
  newPassword: string;
  reNewPassword: string;
}

const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const userInfo = ref<UserInfo>({
  id: 0,
  name: '',
  password: '',
  email: '',
  avatar: '',
  newPassword: '',
  reNewPassword: '',
})
const token = ref(tokenStore.token)
const imgUrl = ref(userInfoStore.info.avatar)
const getUserInfo = async () => {
  const result = await userInfoService();
  userInfo.value = result.data;
}
getUserInfo()
// 自定义表单校验通过validator使用
const confirmRePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== userInfo.value.newPassword) {
    callback(new Error('两次密码不一致'))
  } else {
    callback()
  }
}
const confirmRealPass = async (rule, value, callback) => {
  const result = await confirmPasswordService(value);
  if (value === '') {
    callback(new Error('输入原密码'))
  } else if (!result.data) {
    callback(new Error('密码错误'))
  } else {
    callback()
  }
}
// eP自带表单校验
const rules = reactive<FormRules>({
  name: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 8, message: '请输入3-8位用户名', trigger: 'blur'},
  ],
  password: [
    {asyncValidator: confirmRealPass, trigger: 'blur', required: false}
  ],
  newPassword: [
    {required: false, message: '请输入新密码', trigger: 'blur'},
    {min: 8, max: 16, message: '请输入8-16位密码', trigger: 'blur'},
  ],
  reNewPassword: [
    {validator: confirmRePass, trigger: 'blur', required: false}
  ],
  email: [
    {required: false, message: '请输入邮箱', trigger: 'blur'},
    {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
  ]
})
const changeUserInfo = async () => {
  await updateUserInfoService({
    password: userInfo.value.newPassword,
    email: userInfo.value.email,
    id: userInfo.value.id
  });
  const result = await userInfoService()
  userInfoStore.setInfo(result.data)
  ElMessage.success("修改用户信息成功")
}
const changeUserAvatar = async () => {
  let result = await updateAvatarService(imgUrl.value);
  ElMessage.success("修改头像成功")
  userInfoStore.info.avatar = imgUrl.value;
}
const uploadSuccess = (result) => {
  imgUrl.value = result.data;
}
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style scoped>
.mid-btn {
  margin-left: 100px;
  margin-top: 10px;
  width: 150px;
}
</style>