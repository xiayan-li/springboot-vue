import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import '@/assets/css/global.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

createApp(App).use(store).use(router).use(ElementPlus, {size:'small',}).mount('#app')
// for (const [k, c] of Object.entries(ElementPlusIconsVue)) {
//     App.component(k, c)
// }