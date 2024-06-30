package com.pblog.config;

import com.pblog.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/login/**", "/user/register", "/article/list", "/article/getArticleById/**",
                        "/article/getArticleByCategory/**", "/article/commentsByArticleId/**","/article/getCategory",
                        "/article/hotArticle", "/article/updateArticleViewCounts/**"
                );
    }
}
