import { createApp } from 'vue'
// TypeScript error? Run VSCode command
// TypeScript: Select TypeScript version - > Use Workspace Version
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import './index.css'

createApp(App)
  .use(store)
  .use(router)
  .use(ElementPlus, { size: 'medium' })
  .mount('#app')
