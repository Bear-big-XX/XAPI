# OpenAPI

項目描述：

1. x-backend:总后端
2. x-frontend:前端
3. x-agent:民客户端，网关会将请求转发到这个模拟客户端
4. x-gateway:网关，接受前端的请求并转发

启动顺序：

1. 启动nacos
2. 启动backend
3. 启动gateway
4. 启动agent