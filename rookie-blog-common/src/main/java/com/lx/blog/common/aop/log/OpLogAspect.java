package com.lx.blog.common.aop.log;

import cn.dev33.satoken.stp.StpUtil;
import com.lx.blog.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 操作日志切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class OpLogAspect {

    private final OpLogRegistry registry;
    private final ApplicationEventPublisher publisher;

    @AfterReturning(pointcut = "@annotation(opLog)", returning = "ret")
    public void afterSuccess(JoinPoint jp, OpLog opLog, Object ret) {
        MethodSignature ms = (MethodSignature) jp.getSignature();
        String className = ms.getDeclaringTypeName();
        String methodName = ms.getName();
        String funcId = opLog.func().isEmpty() ? className + "." + methodName : opLog.func();

        // 查找匹配的表
        for (Map.Entry<String, List<String>> e : registry.getTableFunctions().entrySet()) {
            if (e.getValue().contains(funcId)) {
                String table = e.getKey();
                Map<String, Object> payload = buildPayload(opLog, jp, ret);
                publisher.publishEvent(new OpLogEvent(this, table, payload));
            }
        }
    }

    private Map<String, Object> buildPayload(OpLog opLog, JoinPoint jp, Object ret) {
        Map<String, Object> map = new HashMap<>();
        HttpServletRequest req = ServletUtils.getRequest();
        String ip = req.getRemoteAddr();
        String ua = req.getHeader("User-Agent");
        String userId = null;
        try { userId = StpUtil.getLoginIdAsString(); } catch (Exception ignored) {}

        map.put("action", opLog.action().isEmpty() ? jp.getSignature().getName() : opLog.action());
        map.put("user_id", userId);
        map.put("ip", ip);
        map.put("user_agent", ua);
        map.put("logged_at", LocalDateTime.now());
        // 尝试提取articleId
        try {
            MethodSignature ms = (MethodSignature) jp.getSignature();
            String[] names = ms.getParameterNames();
            Object[] args = jp.getArgs();
            for (int i = 0; i < names.length; i++) {
                if ("articleId".equals(names[i]) || ("id".equals(names[i]) && args[i] instanceof String)) {
                    map.put("article_id", String.valueOf(args[i]));
                }
            }
        } catch (Exception ignored) {}
        return map;
    }
}
