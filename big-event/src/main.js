import './assets/main.scss'

import App from './App.vue'
import { createApp } from 'vue'

import Element from "element-plus"
import 'element-plus/dist/index.css'
//引入路由器
import router from "@/router"

//引入pinia
import {createPinia} from 'pinia'
import {createPersistedState} from 'pinia-persistedstate-plugin'
// 分页处理中文
import locale from 'element-plus/dist/locale/zh-cn.js'

const app=createApp(App)
const pinia= createPinia();
const piniaSist=createPersistedState()

pinia.use(piniaSist)
app.use(pinia)
app.use(router)
app.use(Element,{locale })
app.mount('#app')
