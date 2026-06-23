# ============================================================
# SmartKB Backend Dockerfile
# 构建：mvn clean package -DskipTests
# 然后将 target/smartkb-backend-*.jar 复制到本目录
# 重命名为 smartkb-backend.jar
# ============================================================

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 复制 JAR
COPY smartkb-backend.jar app.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
  CMD wget -qO- http://localhost:8080/api/prompts || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]