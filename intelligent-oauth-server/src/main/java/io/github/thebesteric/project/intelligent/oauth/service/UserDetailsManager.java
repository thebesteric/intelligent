package io.github.thebesteric.project.intelligent.oauth.service;

import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.exception.InvalidDataException;
import io.github.thebesteric.project.intelligent.oauth.config.SecurityContextHolder;
import io.github.thebesteric.project.intelligent.oauth.detail.UserDetailsFactory;
import io.github.thebesteric.project.intelligent.oauth.model.domain.CustomUserDetails;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * InDBUserDetailsManager
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 13:33:29
 */
@Slf4j
@RequiredArgsConstructor
public class UserDetailsManager implements UserDetailsService {

    private final UserDetailsFactory userDetailsFactory;

    /**
     * 主入口
     * 返回用户信息，不能返回 CustomUserDetails，否则会报 CustomUserDetails is not in the allowlist
     * 详见：https://github.com/spring-projects/spring-security/issues/4370
     *
     * @param identity 身份凭证
     *
     * @return UserDetails
     *
     * @author wangweijun
     * @since 2025/1/3 15:13
     */
    @Override
    public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        // 根据认证源查找对应的用户信息
        CustomUserDetails userDetails = loadUserByIdentity(identity);
        SecurityContextHolder.setUserDetails(userDetails);
        return new User(identity, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * 获取用户信息
     *
     * @param identity 身份凭证
     *
     * @return CustomUserDetails
     *
     * @author wangweijun
     * @since 2024/4/3 14:45
     */
    public synchronized CustomUserDetails loadUserByIdentity(String identity) {
        AuthSource authSource = SecurityContextHolder.getAuthSource();
        AuthType authType = SecurityContextHolder.getAuthType();
        DataValidator.create()
                .validate(authSource == null, new InvalidDataException("认证源不能为空"))
                .validate(authType == null, new InvalidDataException("认证类型不能为空"));
        return this.loadUserByIdentity(identity, Objects.requireNonNull(authSource), Objects.requireNonNull(authType));
    }

    /**
     * 获取用户信息
     *
     * @param identity   身份凭证
     * @param authSource 认证源
     * @param authType   认证类型
     *
     * @return CustomUserDetails
     *
     * @author wangweijun
     * @since 2024/6/15 19:48
     */
    public synchronized CustomUserDetails loadUserByIdentity(String identity, @NotNull AuthSource authSource, @NotNull AuthType authType) {
        log.info("认证用户信息: identity={}, authSource={}, authType={}", identity, authSource.getSource(), authType.getType());
        return userDetailsFactory.loadUserByIdentity(identity, authSource, authType);
    }
}
