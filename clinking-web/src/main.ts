import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
// 全局导入组件库
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import router from "./router";
import "@/assets/styls/tailwind.css";
// 引入cookie
import VueCookies from "vue-cookies";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

//import router from './router'

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
const pinia = createPinia();

// 挂载应用
app.use(router);
app.use(ElementPlus);
app.use(pinia);
app.use(VueCookies);
app.mount("#app");
