# 1. 使用 Nginx 作为 Web 服务器
FROM nginx:stable-alpine

# 2. 作者信息
LABEL maintainer="ZZUVisitor"

# 3. 删除 Nginx 默认的配置文件
RUN rm /etc/nginx/conf.d/default.conf

# 4. 复制我们要自定义的 Nginx 配置文件（下一步会创建）
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 5. 将 Vue 打包生成的 dist 目录复制到 Nginx 的静态资源目录
# 注意：这假设你已经运行了 npm run build
COPY dist/ /usr/share/nginx/html

# 6. 暴露 80 端口
EXPOSE 80

# 7. 启动 Nginx（daemon off 保证容器不退出）
CMD ["nginx", "-g", "daemon off;"]