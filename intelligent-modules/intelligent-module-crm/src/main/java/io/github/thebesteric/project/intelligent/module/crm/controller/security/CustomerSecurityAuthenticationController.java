package io.github.thebesteric.project.intelligent.module.crm.controller.security;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.annotation.SkipAuth;
import io.github.thebesteric.project.intelligent.core.constant.security.SecurityConstants;
import io.github.thebesteric.project.intelligent.core.model.domain.security.OAuth2Token;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.security.request.CustomerLoginRequest;
import io.github.thebesteric.project.intelligent.module.crm.service.security.CustomerAuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * AuthorizationController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 19:27:34
 */
@AgileLogger
@RestController
@RequestMapping("/security/authentication")
@RequiredArgsConstructor
@Tag(name = "客户-安全-认证相关")
@SkipAuth
public class CustomerSecurityAuthenticationController {

    private final CustomerAuthorizationService authorizationService;

    @PostMapping("/login")
    @Operation(summary = "客户登录")
    public R<OAuth2Token> login(@Validated @RequestBody CustomerLoginRequest loginRequest,
                                @NotBlank(message = "请求认证头不能为空") @RequestHeader(value = SecurityConstants.REQUEST_HEADER_AUTHORIZATION, required = false) String authorization) {
        return authorizationService.login(loginRequest, authorization);
    }

    @PostMapping("/refresh_token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌")
    @Parameter(name = SecurityConstants.REQUEST_HEADER_AUTHORIZATION, in = ParameterIn.HEADER, description = "Basic 认证信息，格式：Basic dGVzdC1jbGllbnQtaWQ6c2VjcmV0")
    public R<OAuth2Token> refreshToken(@RequestParam String refreshToken,
                                       @RequestHeader(value = SecurityConstants.REQUEST_HEADER_AUTHORIZATION) String authorization) {
        return authorizationService.refreshToken(refreshToken, authorization);
    }

}
