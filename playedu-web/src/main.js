import { createApp } from "vue";
import pinia from "./stores";
import router from "./router";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import App from "./App.vue";
import "./assets/css/index.css";
import * as echarts from 'echarts'//引入echarts


const app = createApp(App);
app.config.globalProperties.$echarts = echarts//全局使用
app.use(router).use(pinia).use(ElementPlus);
// router.isReady().then(() => {
// });
app.mount('#app');
// createApp(App).use(router).use(pinia).use(ElementPlus).mount("#app");
