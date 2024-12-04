import { createApp } from "vue";
import App from "./App.vue";

import router from "./router"; // 导入 router 配置
import axios from "axios";
axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.put['Content-Type'] = 'application/json';
axios.defaults.baseURL = "http://192.168.0.104:8086"; // 设置为你的后端服务地址
console.log("Router instance:", router);
const app = createApp(App);

app.use(router); // 使用 router
app.mount("#app");
