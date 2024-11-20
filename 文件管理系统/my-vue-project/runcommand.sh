docker run -d --name my-vue1-container --network mynetwork -p 8084:8082 -v .:/app my-vue-project
docker run -d --name my-vue2-container --network mynetwork -p 8085:8082 -v .:/app my-vue-project
