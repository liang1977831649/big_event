//定制请求的实例
import { ElMessage, inputEmits } from "element-plus";
//导入axios  npm install axios
import axios from "axios";
//定义一个变量,记录公共的前缀 ,  baseURL
const baseURL = "http://localhost:8080"; //代表的就是：http://ip地址:端口号/api
const instance = axios.create({ baseURL });
import { useTokenStore } from "@/store/tokenStore.js";

//配置请求拦截器，带入token
instance.interceptors.request.use((config) => {
  const tokenStore = useTokenStore();
  if (tokenStore.token) {
    //添加token
    config.headers.Authorization = tokenStore.token;
  }

  return config;
});


import router from "@/router";
// const router =useRouter();
//添加响应拦截器
instance.interceptors.response.use(
  (result) => {
    if (result.data.code === 0) {
      // ElMessage.success('操作成功');
      // console.log('result.data=',result.data);
      return result.data;
    } else {
      // alert(result.data.message?result.data.message:"服务器异常");
      ElMessage.error(result.data.message ? result.data.message : "服务器异常");
      return Promise.reject(result.data);
    }
  },
  (err) => {
    if (err.response.status === 401) {
      ElMessage.error("请先登录");
      router.push("/login");
    } else {
      ElMessage.err("服务异常");
    }
    return Promise.reject(err); //异步的状态转化成失败的状态
  }
);

export default instance;
