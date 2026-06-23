package com.smartkb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SmartKB 智能对话平台 启动类
 *
 * <p>应用入口，负责：</p>
 * <ul>
 *   <li>启动 Spring Boot 内嵌 Tomcat</li>
 *   <li>扫描 MyBatis-Plus Mapper 接口</li>
 * </ul>
 *
 * @author codex
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.smartkb.mapper")
public class SmartKbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartKbApplication.class, args);
    }
}

