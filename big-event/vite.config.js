import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // 解决跨域问题
  // server:{
  //   proxy:{
  //     '/api':{//对请求路径含有api字符的源，将原本的http://localhost:8080/api/...替换为http://localhost:8080/...
  //       target:'http://8.138.163.227:8080',
  //       changeOrigin:true,
  //       rewrite:(path)=>path.replace(/^\/api/,"")
  //     }
  //   }
  // },
  // base:'./',
  // host:'8.138.163.227',
  // port:8080
})
