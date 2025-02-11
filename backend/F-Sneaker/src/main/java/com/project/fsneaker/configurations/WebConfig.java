package com.project.fsneaker.configurations;

import com.project.fsneaker.components.LanguageResponseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LanguageResponseInterceptor languageResponseInterceptor;

    public WebConfig(LanguageResponseInterceptor languageResponseInterceptor) {
        this.languageResponseInterceptor = languageResponseInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(languageResponseInterceptor);
    }
}