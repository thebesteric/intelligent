package io.github.thebesteric.project.intelligent.modules.common.processor.impl;

import io.github.thebesteric.project.intelligent.modules.common.processor.SecurityRequestMatcherIgnoredUriProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * 忽略 uris 的抽象类，子类可以直接继承
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-29 12:04:41
 */
public abstract class AbstractSecurityRequestMatcherIgnoredUriProcessor implements SecurityRequestMatcherIgnoredUriProcessor {
    @Override
    public void process(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeHttpRequests) {
        authorizeHttpRequests.requestMatchers(ignoredUris()).permitAll();
    }
}
