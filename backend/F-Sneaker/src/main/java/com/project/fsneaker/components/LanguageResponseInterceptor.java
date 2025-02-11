package com.project.fsneaker.components;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
public class LanguageResponseInterceptor implements HandlerInterceptor {
    private final LocaleResolver localeResolver;

    public LanguageResponseInterceptor(@Qualifier("customLocaleResolver") LocaleResolver localeResolver) {  // Đổi tên qualifier
        this.localeResolver = localeResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Lấy ngôn ngữ từ Accept-Language header
        Locale locale = localeResolver.resolveLocale(request);
        // Thêm ngôn ngữ vào response header
        response.setHeader("Content-Language", locale.getLanguage());
        return true;
    }
}