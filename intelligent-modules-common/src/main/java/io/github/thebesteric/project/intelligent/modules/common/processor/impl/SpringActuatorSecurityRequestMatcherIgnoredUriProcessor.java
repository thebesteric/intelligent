package io.github.thebesteric.project.intelligent.modules.common.processor.impl;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring Actuator 需要配置忽略的 URIS 处理器
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-04-11 12:12:36
 */
@Component
public class SpringActuatorSecurityRequestMatcherIgnoredUriProcessor extends AbstractSecurityRequestMatcherIgnoredUriProcessor {
    @Override
    public String[] ignoredUris() {
        List<String> ignoredUris = List.of("/actuator/**");
        return ignoredUris.toArray(new String[0]);
    }
}
