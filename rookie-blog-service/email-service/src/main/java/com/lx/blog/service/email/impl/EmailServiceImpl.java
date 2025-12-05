package com.lx.blog.service.email.impl;

import com.lx.blog.common.utils.MailUtils;
import com.lx.blog.service.email.EmailService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description 邮件服务实现
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @NotNull private final MailUtils mailUtils;
    @Value("${app.host:http://localhost:8080}")
    private String appHost;

    /**
     * 发送邮箱验证邮件
     * @param userId 用户ID
     * @param email 收件邮箱
     * @param verifyUrl 验证链接
     */
    @Override
    public void sendVerificationEmail(String userId, String email, String verifyUrl) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", userId);
        model.put("verifyUrl", verifyUrl);
        model.put("expireHours", 24);
        mailUtils.sendTemplateMail(email, "邮箱验证", "templates/mail/verify-email.html", model);
    }
}