# 使用 OpenJDK 1.8 官方镜像作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将编译好的 jar 文件复制到镜像中
COPY ./demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# 设置容器启动命令
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]

# 暴露容器的端口
EXPOSE 8086
