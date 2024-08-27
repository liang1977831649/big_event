<script setup>
import { ref } from 'vue'
const userInfo = ref({
    id: 0,
    username: 'zhangsan',
    nickname: 'zs',
    email: 'zs@163.com',
})

const rules = {
    nickname: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min:2,max:10, message: '用户名长度为2-10个非空字符', trigger: 'blur' }
    ],
    email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
        { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
    ]
}
import { updateUserInfo } from '@/api/user.js'
import useUserInfoStore from '@/store/userInfo.js'
import { useRouter } from "vue-router"
import { ElMessage } from 'element-plus'

//从pinia中获取用户信息
const userInfoStore = useUserInfoStore();
userInfo.value = userInfoStore.userInfo;

const router = useRouter();

//修改用户资料
const updateUser = async () => {
    let result = await updateUserInfo(userInfo.value);
    console.log('result.data=', result.data);
    router.push('/user/info');
    ElMessage.success('用户资料更新成功')
}

</script>


<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="登录名称">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="用户邮箱" prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUser()">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>