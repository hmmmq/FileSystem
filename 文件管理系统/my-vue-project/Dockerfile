# 使用 Node.js 22 版本作为基础镜像
FROM node:22

# 设置工作目录为 /app
WORKDIR /app

# 将 package.json 和 package-lock.json 复制到容器内
COPY package*.json ./

# 安装项目依赖
RUN npm install

# 将 Vue 项目的源代码复制到容器内
COPY . .

# 启动静态文件服务，使用 http-server 提供 public 目录中的静态文件
CMD ["npm", "run", "serve"]
