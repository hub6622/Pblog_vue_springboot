import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'
import router from './router/router.js'
import { createPersistedState} from "pinia-persistedstate-plugin";
import * as ElementPlusIconsVue from "@element-plus/icons-vue"

const app = createApp(App)
const pinia = createPinia()
const persist = createPersistedState()
pinia.use(persist)
app.use(pinia)
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)){
    app.component(key, component)
}
app.use(router)
app.mount('#app')

