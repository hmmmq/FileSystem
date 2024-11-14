# FileSystem
山东某厂的文件管理系统

# 部署方案
在一个主机上通过Docker部署完整的前后端和数据库，并通过局域网访问
https://chatgpt.com/share/67361683-1c04-8003-9558-4e496ecff169
https://chatgpt.com/c/67361489-b30c-8003-a55b-710223b0f2a9
1. **准备 Docker Compose 文件：**
   使用 Docker Compose 来简化多个容器的部署。你可以在一个 `docker-compose.yml` 文件中定义所有四个容器（static-server, Vue, Spring Boot, MySQL）。

   示例 `docker-compose.yml` 文件：

   ```yaml
   version: '3.8'
   services:
     static-server:
       image: your-static-server-image
       container_name: static-server
       ports:
         - "8083:80"
       volumes:
         - ./path/to/static/files:/usr/share/nginx/html
       networks:
         - app-network

     vue:
       image: your-vue-image
       container_name: vue
       ports:
         - "8082:80"
       networks:
         - app-network

     springboot:
       image: your-springboot-image
       container_name: springboot
       ports:
         - "8086:8080"
       environment:
         - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/yourdb
         - SPRING_DATASOURCE_USERNAME=root
         - SPRING_DATASOURCE_PASSWORD=rootpassword
       networks:
         - app-network
       depends_on:
         - mysql

     mysql:
       image: mysql:5.7
       container_name: mysql
       environment:
         - MYSQL_ROOT_PASSWORD=rootpassword
         - MYSQL_DATABASE=yourdb
       ports:
         - "3306:3306"
       networks:
         - app-network

   networks:
     app-network:
       driver: bridge
   ```

2. **构建和运行容器：**
   在包含 `docker-compose.yml` 的文件夹中，运行以下命令来构建并启动容器：

   ```bash
   docker-compose up -d
   ```

   这将启动所有容器并在指定的端口映射上运行它们。你可以通过局域网中的其他设备访问前端（例如，`http://<host-ip>:8082`）。

3. **网络配置：**
   确保主机和其他设备位于同一个局域网内，并且主机防火墙允许相应端口的访问。可以使用 `ifconfig` 或 `ipconfig` 查找主机的局域网 IP 地址，其他设备通过该 IP 地址访问。

4. **前端访问：**
   在局域网中的其他设备上，你可以通过 `http://<host-ip>:8082` 访问 Vue 前端，后台通过 `http://<host-ip>:8086` 访问 Spring Boot。

