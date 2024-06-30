<template>
  <div class="login">
    <div class="login-box">
      <el-form
          :label-position="'top'"
          ref="ruleFormRef"
          style="max-width: 300px"
          :model="ruleForm"
          :rules="rules"
          label-width="auto"
          class="demo-ruleForm"
          :size="formSize"
          status-icon
          v-if="isRegister"
      >
        <h1>注册Pblog</h1>
        <el-form-item prop="name">
          <el-input v-model="ruleForm.name" placeholder="用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="ruleForm.password" type="password" autocomplete="off" placeholder="密码"/>
        </el-form-item>
        <el-form-item prop="rePass">
          <el-input v-model="ruleForm.rePass" type="password" autocomplete="off" placeholder="确认密码"/>
        </el-form-item>
        <el-form-item>
          <el-link type="info" @click="goLogin">
            前往登录 ->
          </el-link>
          <el-button class="right-btn" type="primary" @click="register">
            注册
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
        <div class="welcome">
          <p>欢迎加入<span>Pblog</span></p>
        </div>
      </el-form>
      <el-form
          :label-position="'top'"
          ref="ruleFormRef"
          style="max-width: 300px"
          :model="ruleForm"
          :rules="rules"
          label-width="auto"
          class="demo-ruleForm"
          :size="formSize"
          status-icon
          autocomplete="off"
          v-else
      >
        <h1>登录Pblog</h1>
        <el-form-item prop="name">
          <el-input v-model="ruleForm.name" placeholder="用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="ruleForm.password" type="password" autocomplete="off" placeholder="密码"/>
        </el-form-item>
        <el-form-item>
          <el-link type="info" @click="goRegister">
            前往注册 ->
          </el-link>
          <el-button class="right-btn" type="primary" @click="login">
            登录
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
        <div class="welcome">
          <p>本站今日登录人数</p>
        </div>
        <div class="welcome">
          <p>今天也是美好的一天<span>Pblog</span></p>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'
import {ElMessage} from "element-plus";
import router from "@/router/router"

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
  name: '',
  password: '',
  rePass: ''
})
// 自定义表单校验通过validator使用
const confirmRePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== ruleForm.password) {
    callback(new Error('两次密码不一致'))
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
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 8, max: 16, message: '密码8-16位', trigger: 'blur'},
  ],
  rePass: [
    {validator: confirmRePass, trigger: 'blur'}
  ]
})
// isRegister确认是注册表单还是登录表单，可自动跳转登录
const isRegister = ref(false)
const goLogin = () => {
  isRegister.value = false;
}
const goRegister = () => {
  ruleForm.name = ''
  ruleForm.password = ''
  ruleForm.rePass = ''
  isRegister.value = true;
}

// 使用axios发送的登录和注册请求
import {userRegisterService, userLoginService} from '@/api/user.js'
import {useTokenStore} from '@/stores/token'
import {useUserInfoStore} from '@/stores/userInfo.js'
import {userInfoService} from '@/api/user.js'

const useUserInfo = useUserInfoStore()
const tokenStore = useTokenStore()
const getUserInfo = async () => {
  let result = await userInfoService()
  useUserInfo.setInfo(result.data)
}
const login = async () => {
  const result = await userLoginService(ruleForm) as any;
  if (result.code === 200){
    ElMessage.success(result ? result.msg : "登录成功")
    tokenStore.setToken(result.data)
    getUserInfo()
    router.push('/')
  }else {
    ElMessage.error(result ? result.msg : "登录失败")
  }
}
const register = async () => {
  //  as any解决result无msg code假报错问题
  const result = await userRegisterService(ruleForm) as any;
  ElMessage.success(result ? result.msg : "注册成功")
  // 自动跳转登录
  isRegister.value = false
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style scoped>
.login {
  top: 0;
  left: 0;
  background-size: 100% 100%;
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url(../statis/loginBGI.jpg);
}

.login-box {
  border-radius: 10px;
  box-shadow: 0px 0px 1px 1px pink;
  position: absolute;
  width: 300px;
  height: 310px;
  background-color: white;
  margin-top: 250px;
  margin-left: -180px;
  left: 50%;
  padding: 30px;
}

.login-box h1 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  vertical-align: middle;
}

.login-box .welcome {
  text-align: center;
  font-size: 18px;
}

.login-box .welcome span {
  color: pink;
  text-align: center;
  font-size: 24px;
}

.right-btn {
  margin-left: 30%;
}
</style>