package io.github.thebesteric.project.intelligent.oauth.detail;

import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.oauth.model.domain.CustomUserDetails;

public interface CustomUserDetailsService {

    /**
     * 认证源
     *
     * @return AuthSource
     *
     * @author wangweijun
     * @since 2024/5/22 15:28
     */
    AuthSource getAuthSource();

    /**
     * 认证类型
     *
     * @return AuthType
     *
     * @author wangweijun
     * @since 2024/5/22 15:28
     */
    AuthType getAuthType();

    /**
     * 根据登录凭证获取用户
     *
     * @param identity 身份凭证
     *
     * @return CustomUserDetails
     *
     * @author wangweijun
     * @since 2024/5/21 19:44
     */
    CustomUserDetails loadUserByIdentity(String identity);
}
