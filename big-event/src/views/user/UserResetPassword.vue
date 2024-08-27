<script setup>

import { ref } from 'vue';

const ModifyPassword = ref({
    old_pwd: "",
    new_pwd: "",
    re_pwd: ""
});

const userInfoStore=ref({
    id: 0,
    username: 'zhangsan',
    nickname: 'zs',
    email: 'zs@163.com',
})

const userInfo = ref(userInfoStore.userInfo); // 确保这里获取到了用户信息

const checkNewAndOld = (rule, value, callback) => {
    // console.log( ModifyPassword.value.new_pwd);
    if (value === '') {
        callback(new Error('请输入确认密码'));
    } else if (value !== ModifyPassword.value.new_pwd) { // 确认密码和新的密码应该一致
        callback(new Error('确认密码和新密码必须一致'));
    } else {
        callback(); // 如果所有条件都满足，则校验通过
    }
};

const rules = {
    old_pwd: [ // 原始密码的校验规则保持不变
        { required: true, message: '请输入原密码', trigger: 'blur' }
    ],
    new_pwd: [ // 新密码的校验规则保持不变（这里只是示例）
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 5, max: 16, message: '密码长度为5-16的非空字符串', trigger: 'blur' }
    ],
    re_pwd: [ // 确认密码的校验规则需要调整，添加自定义校验器进行验证
        { required: true, message: '请输入确认密码', trigger: 'blur' },
        { validator: checkNewAndOld, trigger: 'blur' }
        // { min: 5, max: 16, message: '密码长度为5-16的非空字符串', trigger: 'blur' } 
    ]
};


import { updatePassword } from "@/api/user.js"
import { ElMessage } from "element-plus"
import { useRouter } from 'vue-router';
import { useTokenStore } from "@/store/tokenStore"
import useUserInfoStore from '@/store/userInfo.js';

const router = useRouter();
const tokenStore = useTokenStore();
userInfoStore.value = useUserInfoStore();

const updatePwd = async () => {
    // console.log(ModifyPassword.value.old_pwd);
    // console.log(ModifyPassword.value.new_pwd);
    // console.log(ModifyPassword.value.re_pwd);

    if (ModifyPassword.value.old_pwd === '' || ModifyPassword.value.new_pwd === '' || ModifyPassword.value.re_pwd === '') {
        ElMessage.info('请将信息补全');
        return;
    }

    let result = await updatePassword(ModifyPassword.value);
    console.log('result=');
    ElMessage.success('修改密码成功');
    //需要让用户重新登陆
    //1.清除token
    tokenStore.removeToken();
    //2.清楚userInfo
    userInfoStore.removeUserInfo();
    //3.退出到登录页
    router.push('/login');
}
</script>


<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>修改密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form ref="form" :model="ModifyPassword" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="用户名" >
                        <el-input v-model="userInfoStore.userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="原始密码" prop="old_pwd">
                        <el-input type="password" v-model="ModifyPassword.old_pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新的密码" prop="new_pwd">
                        <el-input type="password" v-model="ModifyPassword.new_pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="re_pwd">
                        <el-input type="password" v-model="ModifyPassword.re_pwd"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="updatePwd()">修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>