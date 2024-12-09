package io.github.thebesteric.project.intelligent.modules.common.processor.impl;

import io.github.thebesteric.project.intelligent.core.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Swagger 需要配置忽略的 URIS 处理器
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-29 12:07:14
 */
@Component
@RequiredArgsConstructor
public class SwaggerSecurityRequestMatcherIgnoredUriProcessor extends AbstractSecurityRequestMatcherIgnoredUriProcessor {

    private final ApplicationProperties applicationProperties;

    @Override
    public String[] ignoredUris() {
        // application.components.swagger,enable = false
        if (!applicationProperties.getComponents().getSwagger().isEnable()) {
            return new String[0];
        }
        // 设置 swagger 需要放行的资源
        List<String> ignoredUris = List.of("/doc.html", "/swagger-ui/**", "/webjars/**", "/swagger-resources/**", "/v3/api-docs/**");
        return ignoredUris.toArray(new String[0]);
    }
}
