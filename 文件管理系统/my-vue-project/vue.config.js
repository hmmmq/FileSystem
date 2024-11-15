const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8082,
    host: '0.0.0.0',         // 绑定到所有可用的网络接口
    allowedHosts: 'all',     // 允许所有主机访问
  },
});
