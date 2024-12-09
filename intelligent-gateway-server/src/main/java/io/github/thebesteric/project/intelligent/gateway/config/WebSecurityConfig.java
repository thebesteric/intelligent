package io.github.thebesteric.project.intelligent.gateway.config;

import io.github.thebesteric.project.intelligent.core.properties.OAuth2Properties;
import io.github.thebesteric.project.intelligent.gateway.handler.BusinessAccessDeniedHandler;
import io.github.thebesteric.project.intelligent.gateway.handler.LoginAuthenticationEntryPoint;
import io.github.thebesteric.project.intelligent.gateway.handler.TokenAuthenticationFailureHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 资源服务器配置
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig {

    @Resource
    private OAuth2Properties oAuth2Properties;

    /**
     * 配置认证相关的过滤器链
     */
    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {
        // 全部请求都需要认证
        http.authorizeExchange(authorize -> authorize.anyExchange().permitAll());

        // 开启 OAuth2 登录
        http.oauth2Login(Customizer.withDefaults());

        // 设置当前服务为资源服务，解析请求头中的token
        http.oauth2ResourceServer(resourceServer -> resourceServer
                // 使用jwt
                .jwt(Customizer.withDefaults())
                // 请求未携带 token 处理
                .authenticationEntryPoint(new LoginAuthenticationEntryPoint())
                // 权限不足处理
                .accessDeniedHandler(new BusinessAccessDeniedHandler())
                // token 解析失败处理
                .authenticationFailureHandler(new TokenAuthenticationFailureHandler())
        );

        // 关闭跨站请求伪造
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        // 解决跨域问题
        http.cors(ServerHttpSecurity.CorsSpec::disable);

        return http.build();
    }

    /**
     * 跨域配置
     * 获取在 Controller 上使用 @CrossOrigin 注解
     */
    @Bean
    public CorsWebFilter corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        OAuth2Properties.Cors cors = oAuth2Properties.getCors();
        configuration.setAllowedOrigins(cors.getAllowedOrigins());
        configuration.setAllowedMethods(cors.getAllowedMethods());
        configuration.setAllowedHeaders(cors.getAllowedHeaders());
        configuration.setAllowCredentials(cors.isAllowCredentials());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsWebFilter(source);
    }

}
