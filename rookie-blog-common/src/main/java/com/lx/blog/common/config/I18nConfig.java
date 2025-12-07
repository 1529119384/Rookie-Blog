package com.lx.blog.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @author LX
 * @date 2025/12/03
 * @description 国际化配置类
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    /**
     * 配置消息源，用于国际化消息的加载和解析
     *
     * @return 配置好的消息源对象
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:i18n/messages", "classpath:i18n/ValidationMessages");
        ms.setDefaultEncoding("UTF-8");
        ms.setFallbackToSystemLocale(false);
        return ms;
    }

    /**
     * 配置语言解析器，用于解析请求头中的语言标签或会话中的语言设置
     *
     * @return 配置好的语言解析器对象
     */
    @Bean
    public LocaleResolver localeResolver() {
        HeaderOrSessionLocaleResolver resolver = new HeaderOrSessionLocaleResolver();
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return resolver;
    }

    /**
     * 配置验证器，用于验证请求参数和对象
     *
     * @param messageSource 消息源，用于国际化验证错误消息
     * @return 配置好的验证器对象
     */
    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }

    /**
     * 配置语言变更拦截器，用于处理请求参数中的语言变更
     *
     * @return 配置好的语言变更拦截器对象
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * 配置拦截器，将语言变更拦截器添加到拦截器注册表中
     *
     * @param registry 拦截器注册表对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
