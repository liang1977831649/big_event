import { defineStore } from "pinia"
import {ref} from 'vue'

const useUserInfoStore=defineStore("userInfo",()=>{
    const userInfo=ref({})

    const setUserInfo=(newUserInfo)=>{
        userInfo.value=newUserInfo
    }
    const removeUserInfo=()=>{
        userInfo.value={}
    }
    return {userInfo,setUserInfo,removeUserInfo}
},{persist:true})

export default useUserInfoStore