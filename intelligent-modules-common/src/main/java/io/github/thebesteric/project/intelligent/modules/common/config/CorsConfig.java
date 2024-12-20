package io.github.thebesteric.project.intelligent.modules.common.config;

import io.github.thebesteric.project.intelligent.core.annotation.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-04-30 12:08:28
 */
@Configuration
@ConditionalOnProperties({
        @ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev"),
        @ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "test"),
        @ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "prod")
})
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 对所有路径生效
                .addMapping("/**")
                // 允许所有源地址
                .allowedOrigins("*")
                // 允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                // 允许的请求头
                .allowedHeaders("*");
    }
}
