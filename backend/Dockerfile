# 1. 基础镜像：你的日志显示你用的是 Java 21，所以我们用 OpenJDK 21 的精简版
FROM openjdk:21-jdk-slim

# 2. 作者信息
LABEL maintainer="ZZUVisitor"

# 3. 设置容器内的工作目录
WORKDIR /app

# 4. 挂载临时卷（Spring Boot 推荐做法，用于 Tomcat 临时文件）
VOLUME /tmp

# 5. 将构建好的 JAR 包复制到容器中
# 注意：这假设你已经运行了 mvn package，JAR 包在 target 目录下
COPY target/*.jar app.jar

# 6. 暴露后端端口
EXPOSE 8081

# 7. 启动命令
# 这里的参数允许随机数生成加速，并指定时区为上海
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Asia/Shanghai", "-jar", "app.jar"]