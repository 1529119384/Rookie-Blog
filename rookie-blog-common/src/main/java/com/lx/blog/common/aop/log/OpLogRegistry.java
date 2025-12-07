package com.lx.blog.common.aop.log;

import com.lx.blog.common.config.OpLogRegistryConfig;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author LX
 * @date 2025/12/03
 * @description 操作日志函数映射注册表
 */
@Component
public class OpLogRegistry {

    @Getter
    private final Map<String, List<String>> tableFunctions = new HashMap<>();

    @PostConstruct
    public void init() {
        register("user_log", OpLogRegistryConfig.OP_LOG_REGISTRY_USER_LOG_FUNC_LIST);
        register("article_log", OpLogRegistryConfig.OP_LOG_REGISTRY_ARTICLE_LOG_FUNC_LIST);
    }

    public void register(String table, List<String> funcs) {
        tableFunctions.computeIfAbsent(table, k -> new ArrayList<>()).addAll(funcs);
    }
}
