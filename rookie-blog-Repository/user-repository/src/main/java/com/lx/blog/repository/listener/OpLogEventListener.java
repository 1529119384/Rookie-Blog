package com.lx.blog.repository.listener;

import com.lx.blog.common.aop.log.OpLogEvent;
import com.lx.blog.repository.dao.impl.mapper.LoginLogMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.LoginLog;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description 操作日志事件监听器
 */
@Component
@RequiredArgsConstructor
public class OpLogEventListener {

    @NotNull
    private final LoginLogMapper loginLogMapper;

    @EventListener
    public void onOpLog(OpLogEvent event) {
        if (!"login_logs".equals(event.getTableName())) return;
        Map<String, Object> p = event.getPayload();
        LoginLog log = LoginLog.builder()
                .userId((String) p.get("user_id"))
                .ip((String) p.get("ip"))
                .userAgent((String) p.get("user_agent"))
                .loggedAt((LocalDateTime) p.get("logged_at"))
                .build();
        loginLogMapper.insert(log);
    }
}

