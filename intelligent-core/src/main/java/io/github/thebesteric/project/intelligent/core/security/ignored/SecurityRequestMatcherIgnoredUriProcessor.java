package io.github.thebesteric.project.intelligent.core.security.ignored;

/**
 * 配置需要忽略的 URI 处理器
 *
 * @author wangweijun
 * @since 2024/3/29 11:53
 */
public interface SecurityRequestMatcherIgnoredUriProcessor extends SecurityRequestMatcherProcessor {
    /** 配置忽略的接口 */
    String[] ignoredUris();
}
