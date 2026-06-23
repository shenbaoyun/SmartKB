# ============================================================
# SmartKB Frontend Dockerfile
# 构建：cd ../../frontend && npm run build
# 将 dist/ 目录复制到本目录
# ============================================================

FROM nginx:alpine

# 复制静态文件
COPY dist/ /usr/share/nginx/html/

# 复制 Nginx 配置（含 /api 反向代理）
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost/ || exit 1

CMD ["nginx", "-g", "daemon off;"]