package com.lx.blog.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

/**
 * @author LX
 * @date 2025/12/03
 * @description 会话或请求头中的语言解析器
 */
public class HeaderOrSessionLocaleResolver extends AcceptHeaderLocaleResolver {

    /**
     * 解析请求头中的语言标签，若不存在则返回默认语言
     *
     * @param request HTTP 请求对象
     * @return 解析后的 Locale 对象
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getHeader("lang");
        if (StringUtils.isNotBlank(lang)) {
            return parseLocale(lang);
        }
        return super.resolveLocale(request);
    }

    /**
     * 解析语言标签字符串为 Locale 对象
     *
     * @param lang 语言标签字符串，例如 "zh-CN"、"en-US" 等
     * @return 对应的 Locale 对象
     */
    private Locale parseLocale(String lang) {
        String normalized = lang.trim().toLowerCase().replace('-', '_');
        switch (normalized) {
            case "zh":
                return Locale.SIMPLIFIED_CHINESE;
            case "en":
                return Locale.US;
            default:
                String tag = normalized.replace('_', '-');
                Locale locale = Locale.forLanguageTag(tag);
                return locale.equals(Locale.forLanguageTag("")) ? Locale.SIMPLIFIED_CHINESE : locale;
        }
    }
}
