//引入axios
import request from "@/utils/request.js"
//定义一个函数吗，发送请求，async做异步处理
export const register = async (registerBody)=>{
    //为了避免传参数的格式是json，因此需要使用URLSearchParam
    const param=new URLSearchParams;
    for(var key in registerBody){
        param.append(key,registerBody[key])
    }
    return await request.post("/user/register",param);
}
export const login=async (registerBody)=>{
    const param=new URLSearchParams;
    for(var key in registerBody){
        param.append(key,registerBody[key])
    }
    return await request.post("/user/login",param)
}
//得到用户信息
export const getUserInfo=async  ()=>{
    const userInfo=await  request.get('/user/userInfo');
    return userInfo;
}

//修改用户信息
export const updateUserInfo=async (user)=>{
    return await request.put('/user/update',user)
}

//修改用户头像
export const updateAvatar=async (avatar)=>{
    const param=new URLSearchParams();
    console.log("avatar==",avatar);
    param.append("avatarUrl",avatar)
    return await request.patch('/user/updateAvatar',param);
}

//修改用户密码 
export const updatePassword=async (modifyPassword)=>{
    // const param=new URLSearchParams();
    // for(var key in modifyPassword){
    //     param.append(key,modifyPassword[key])
    // }
    console.log('modifyPassword=',modifyPassword);
    return await  request.patch('/user/updatePwd',modifyPassword);
}