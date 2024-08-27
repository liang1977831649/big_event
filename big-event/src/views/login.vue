<script  setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {ElMessage} from "element-plus"
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const registerBody=ref({
    username:'',
    password:'',
    rePassword:''
})
//定义校验函数，用于校验注册时前后输入的密码是否一致
const checkPassword=(rule,value,callback)=>{
    //rule表示当前规则，value代表的是值，callback表示如果失败了，那几回调
    if(value===''){
        callback(new Error('请输入确认密码'));
    }else if(value!==registerBody.value.password){
        callback(new Error( '前后输入的密码不一致'))
    }else{
        callback();//正常回退
    }
}

//定义校验规则
const rules={
    username:[
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 5, max: 16, message: '用户名长度为5-16的非空字符串', trigger: 'blur' },
    ],
    password:[
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度为5-16的非空字符串', trigger: 'blur' },
    ],
    rePassword:[
        {validator:checkPassword, trigger: 'blur'}
    ]
}

//定义用户注册的函数
import { register ,login} from "@/api/user.js";
const registerClick= async () =>{
    let result= await register(registerBody.value);
    // alert(result.message?result.message:'注册成功');
    ElMessage.success(result.message?result.message:'注册成功')
    // if(result.code===0){
        isRegister.value=false
    // }else if(result.code===1){
    //     alert('注册失败')   
    // }
}
//定义用户登陆函数
//导入token
import {useTokenStore} from '@/store/tokenStore.js'
import { useRouter } from 'vue-router'

const router= useRouter();
const loginClick= async ()=>{
    let result =await login(registerBody.value);
    // alert(result.message?result.message:"登录成功");
    useTokenStore().setToken(result.data);
    ElMessage.success("登录成功")
    //切换路由
    router.push('/');
    
    // console.log("result="+result);
    // if(result.code===0){
    //     alert("登陆成功")
    // }else{
        
    // }
}

const clearRegisterBody=()=>{
    registerBody.value.username=''
    registerBody.value.password=''
    registerBody.value.rePassword=''
}
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerBody" :rules="rules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerBody.username">
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerBody.password">
                    </el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerBody.rePassword">
                    </el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="registerClick">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterBody()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerBody" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerBody.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerBody.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="loginClick">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterBody()">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>