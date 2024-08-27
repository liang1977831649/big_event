import { createRouter, createWebHistory } from "vue-router";

//导入组件
import Login from "@/views/login.vue";
import Layout from "@/views/layout.vue";

import UserAvatar from "@/views/user/UserAvatar.vue";
import UserInfo from "@/views/user/UserInfo.vue";
import UserResetPassword from "@/views/user/UserResetPassword.vue";

import ArticleCategory from "@/views/article/ArticleCategory.vue";
import ArticleManage from '@/views/article/ArticleManage.vue'

//创建URL路径和vue的路由关系
const routes = [
  //配置子路由
  {
    path: "/",
    component: Layout,
    redirect: "/article/manager",
    children: [
      { path: "/article/category", component: ArticleCategory },
      { path: "/article/manager", component: ArticleManage },
      { path: "/user/info", component: UserInfo },
      { path: "/user/avatar", component: UserAvatar },
      { path: "/user/password", component: UserResetPassword },
    ],
  },
  { path: "/login", component: Login },
];

//创建路由器
const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

export default router;
