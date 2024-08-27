<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import {ref} from 'vue'
import avatar from '@/assets/default.png'
import useUserInfoStore from '@/store/userInfo.js'
import {useTokenStore} from '@/store/tokenStore'
const uploadRef = ref()

//用户头像地址
const tokenStore = useTokenStore();
const userInfo=ref({});
const userInfoStore = useUserInfoStore();
userInfo.data=userInfoStore.userInfo;

const imgUrl= ref(userInfo.data.userPic)

const uploadSuccess=(result)=>{
    console.log('result.data=',result.data);
    imgUrl.value=result.data
    console.log('imgUrl.data=',imgUrl.data);
}

import {updateAvatar} from '@/api/user.js'
import {ElMessage} from 'element-plus'
const updateAvatars= async ()=>{
    // imgUrl=userInfo.data.userPic
    console.log('imgUrl.value=',imgUrl.value);
    let result= await updateAvatar(imgUrl.value)
    ElMessage.success('用户头像修改成功')

    //修改userInfoStore
    userInfoStore.userInfo.userPic=imgUrl.value
}

</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>更换头像</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-upload 
                    ref="uploadRef"
                    class="avatar-uploader" 
                    :show-file-list="false"
                    :auto-upload="true"
                    action="http://127.0.0.1:8080/upload"
                    name="multipartFile"
                    :headers="{'Authorization':tokenStore.token}"
                    :on-success="uploadSuccess">
                    <img v-if="imgUrl" :src="imgUrl" class="avatar" />
                    <img v-else :src="avatar" width="278" />
                </el-upload>
                <br />
                <el-button type="primary" :icon="Plus" size="large"  @click="uploadRef.$el.querySelector('input').click()">
                    选择图片
                </el-button>
                <el-button type="success" :icon="Upload" size="large" @click="updateAvatars()">
                    上传头像
                </el-button>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>