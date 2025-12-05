package com.lx.blog.common.aop.log;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 操作日志函数映射注册表
 */
@Component
public class OpLogRegistry {

    @Getter
    private final Map<String, List<String>> tableFunctions = new HashMap<>();

    @PostConstruct
    public void init() {
        register("login_logs", Arrays.asList(
                "user.auth.login",
                "com.lx.blog.service.auth.biz.UserAuthBizService.login"
        ));
        register("article_view", Arrays.asList(
                "article.view"
        ));
    }

    public void register(String table, List<String> funcs) {
        tableFunctions.computeIfAbsent(table, k -> new ArrayList<>()).addAll(funcs);
    }
}
