package io.github.thebesteric.project.intelligent.module.crm.service.security.impl;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import io.github.thebesteric.project.intelligent.core.constant.security.Scope;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.model.domain.security.OAuth2Token;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.core.service.crm.CustomerService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.security.request.CustomerLoginRequest;
import io.github.thebesteric.project.intelligent.module.crm.service.security.CustomerAuthorizationService;
import io.github.thebesteric.project.intelligent.modules.common.feign.SecurityAuthenticationOpenApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

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

    private final CustomerService customerService;
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
        String tenantId = loginRequest.getTenantId();
        String username = loginRequest.getUsername();
        // 获取用户
        Customer customer = customerService.getByUsername(tenantId, username);
        if (customer == null) {
            throw new DataNotFoundException("未授权的访问");
        }
        // 获取 HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 获取认证信息
        R<OAuth2Token> response = securityAuthenticationOpenApi.accessToken(username, loginRequest.getPassword(),
                GrantType.GRANT_TYPE_PASSWORD.getType(),
                AuthType.PASSWORD.getType(), AuthSource.MODULE_CRM.getSource(),
                Scope.getScopesStr(Scope.PROFILE), authorization);
        // 设置登录信息
        if (response.isSucceed()) {
            customer.setSuccessLoginInfo(request);
        } else {
            customer.setErrorLoginInfo(request);
        }
        // 更新用户信息
        customerService.updateById(customer);
        return response;
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
