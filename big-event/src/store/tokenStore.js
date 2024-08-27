import { defineStore } from "pinia";
import {ref} from 'vue'

export const useTokenStore=defineStore('pinia',()=>{
    const token =ref('')
    
    //设置token
    const setToken=(newToken)=>{
        token.value=newToken
    }

    //清除token
    const removeToken= ()=>{
        token.value=''
    }

    //返回token
    return {
        token,setToken,removeToken
    }

},{persist:true})

