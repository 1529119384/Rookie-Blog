package com.lx.blog.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author LX
 * @date 2025/12/7
 * @description 操作日志注册配置类
 */
@Data
@Component
@AllArgsConstructor
public class OpLogRegistryConfig {

    public static final List<String> OP_LOG_REGISTRY_USER_LOG_FUNC_LIST = List.of(
            "user.auth.login",
            "user.auth.register",
            "user.auth.logout",
            "user.auth.updatePassword"
    );

    public static final List<String> OP_LOG_REGISTRY_ARTICLE_LOG_FUNC_LIST = List.of(
            "article.view",
            "article.publish",
            "article.delete",
            "article.draft",
            "article.comments",
            "article.react",
            "article.reply"
    );

}
