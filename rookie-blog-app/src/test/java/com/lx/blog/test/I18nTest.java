package com.lx.blog.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class I18nTest {

    @Test
    public void testMessages() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:i18n/messages", "classpath:i18n/ValidationMessages");
        ms.setDefaultEncoding("UTF-8");
        String zh = ms.getMessage("email.verify.success", null, Locale.SIMPLIFIED_CHINESE);
        String en = ms.getMessage("email.verify.success", null, Locale.US);
        Assertions.assertEquals("邮箱验证成功", zh);
        Assertions.assertEquals("Email verified successfully", en);
    }
}
