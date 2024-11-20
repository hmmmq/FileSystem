# 使用官方 MySQL 8.4.3 镜像
FROM mysql:8.0

# 设置环境变量
ENV MYSQL_ROOT_PASSWORD=12345678
ENV MYSQL_DATABASE=filesys

# 将 SQL 文件复制到容器内的初始化目录
COPY ./mysqldb.sql /docker-entrypoint-initdb.d/

# 暴露 MySQL 默认端口
EXPOSE 3306
