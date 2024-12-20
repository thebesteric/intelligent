package io.github.thebesteric.project.intelligent.core.security.ignored.impl;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 静态资源 需要配置忽略的 URIS 处理器
 *
 * @author caijing
 * @version v1.0
 * @since 2024-04-15 12:07:14
 */
@Component
public class StaticResourceRequestMatcherIgnoredUriProcessor extends AbstractSecurityRequestMatcherIgnoredUriProcessor {

    @Override
    public String[] ignoredUris() {
        // 设置 images 需要放行的资源
        List<String> ignoredUris = List.of("/resource/**");
        return ignoredUris.toArray(new String[0]);
    }
}
