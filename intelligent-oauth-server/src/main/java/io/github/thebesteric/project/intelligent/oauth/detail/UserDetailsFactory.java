package io.github.thebesteric.project.intelligent.oauth.detail;

import com.google.common.collect.HashBasedTable;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.security.SecurityAuthenticationException;
import io.github.thebesteric.project.intelligent.oauth.model.domain.CustomUserDetails;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserDetailsFactory
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-27 19:57:31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsFactory {

    private final HashBasedTable<AuthSource, AuthType, CustomUserDetailsService> services = HashBasedTable.create();

    @Resource
    private final List<CustomUserDetailsService> userDetailsServices;

    @PostConstruct
    public void register() {
        for (CustomUserDetailsService userDetailsService : userDetailsServices) {
            AuthSource authSource = userDetailsService.getAuthSource();
            AuthType authType = userDetailsService.getAuthType();
            services.put(authSource, authType, userDetailsService);
            log.info("注册认证服务: 认证源: {}, 认证类型: {}", authSource.getSource(), authType.getType());
        }
    }

    /**
     * 根据身份凭证获取用户
     *
     * @param identity   身份凭证
     * @param authSource 认证源
     * @param authType   认证类型
     *
     * @return CustomUserDetails
     *
     * @author wangweijun
     * @since 2024/5/22 15:24
     */
    public CustomUserDetails loadUserByIdentity(String identity, AuthSource authSource, AuthType authType) {
        CustomUserDetailsService customUserDetailsService = services.get(authSource, authType);
        if (customUserDetailsService == null) {
            throw new SecurityAuthenticationException("没有找到合适的认证服务: 认证源: %s, 认证类型: %s".formatted(authSource.getSource(), authType.getType()));
        }
        return customUserDetailsService.loadUserByIdentity(identity);
    }
}
