package com.lx.blog.common.aop.log;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author LX
 * @date 2025/12/03
 * @description 操作日志事件
 */
@Getter
public class OpLogEvent extends ApplicationEvent {

    private final String tableName;
    private final Map<String, Object> payload;

    public OpLogEvent(Object source, String tableName, Map<String, Object> payload) {
        super(source);
        this.tableName = tableName;
        this.payload = payload;
    }

}

