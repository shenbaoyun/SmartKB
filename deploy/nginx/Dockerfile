# ============================================================
# Nginx 网关 Dockerfile（可选，--profile gateway 启用）
# 用于统一入口 / SSL 终止 / 负载均衡
# ============================================================

FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80 443