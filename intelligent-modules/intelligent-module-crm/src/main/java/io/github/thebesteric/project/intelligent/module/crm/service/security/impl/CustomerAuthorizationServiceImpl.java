package io.github.thebesteric.project.intelligent.module.crm.service.security.impl;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import io.github.thebesteric.project.intelligent.core.constant.security.Scope;
import io.github.thebesteric.project.intelligent.core.model.domain.security.OAuth2Token;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.security.request.CustomerLoginRequest;
import io.github.thebesteric.project.intelligent.module.crm.service.security.CustomerAuthorizationService;
import io.github.thebesteric.project.intelligent.modules.common.feign.SecurityAuthenticationOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerAuthorizationServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 19:44:47
 */
@Service
@RequiredArgsConstructor
public class CustomerAuthorizationServiceImpl implements CustomerAuthorizationService {

    private final SecurityAuthenticationOpenApi securityAuthenticationOpenApi;

    /**
     * 客户登录
     *
     * @param loginRequest 请求
     *
     * @return CustomerLoginResponse
     *
     * @author wangweijun
     * @since 2025/1/2 19:45
     */
    @Override
    public R<OAuth2Token> login(CustomerLoginRequest loginRequest, String authorization) {

        return securityAuthenticationOpenApi.accessToken(loginRequest.getUsername(), loginRequest.getPassword(),
                GrantType.GRANT_TYPE_PASSWORD.getType(),
                AuthType.PASSWORD.getType(), AuthSource.MODULE_CRM.getSource(),
                Scope.getScopesStr(Scope.PROFILE), authorization);
    }

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
    @Override
    public R<OAuth2Token> refreshToken(String refreshToken, String authorization) {
        return securityAuthenticationOpenApi.refreshToken(refreshToken, authorization);
    }
}
