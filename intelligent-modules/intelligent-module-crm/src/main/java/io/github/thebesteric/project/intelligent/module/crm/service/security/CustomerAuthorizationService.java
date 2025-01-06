package io.github.thebesteric.project.intelligent.module.crm.service.security;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.core.model.domain.security.OAuth2Token;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.security.request.CustomerLoginRequest;

public interface CustomerAuthorizationService {

    /**
     * 客户登录
     *
     * @param loginRequest  请求
     * @param authorization 请求认证头
     *
     * @return CustomerLoginResponse
     *
     * @author wangweijun
     * @since 2025/1/2 19:45
     */
    R<OAuth2Token> login(CustomerLoginRequest loginRequest, String authorization);

    /**
     * 客户刷新令牌
     *
     * @param refreshToken  刷新令牌
     * @param authorization 请求认证头
     *
     * @return CustomerLoginResponse
     *
     * @author wangweijun
     * @since 2025/1/3 17:23
     */
    R<OAuth2Token> refreshToken(String refreshToken, String authorization);
}
