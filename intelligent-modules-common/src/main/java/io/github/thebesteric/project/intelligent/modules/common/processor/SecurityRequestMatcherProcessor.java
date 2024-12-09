package io.github.thebesteric.project.intelligent.modules.common.processor;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * 配置需要 RequestMatcherRegistry 处理器
 *
 * @author wangweijun
 * @since 2024/3/29 11:53
 */
public interface SecurityRequestMatcherProcessor {
    void process(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeHttpRequests);
}
