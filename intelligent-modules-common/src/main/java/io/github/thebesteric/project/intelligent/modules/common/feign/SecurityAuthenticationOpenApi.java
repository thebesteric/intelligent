package io.github.thebesteric.project.intelligent.modules.common.feign;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.core.constant.security.SecurityConstants;
import io.github.thebesteric.project.intelligent.core.model.domain.security.OAuth2Token;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CoreOpenApi
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 20:53:52
 */
@FeignClient(name = "security-authentication-open-api", url = "${application.open-apis.open-api}")
public interface SecurityAuthenticationOpenApi {

    @PostMapping("/security/authentication/access_token")
    @Operation(summary = "获取 Token")
    R<OAuth2Token> accessToken(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(value = "grant_type", defaultValue = "authorization_password") String grantType,
                               @RequestParam(value = "auth_type", defaultValue = "password") String authType,
                               @RequestParam(value = "auth_source", defaultValue = "intelligent-core-api") String authSource,
                               @RequestParam(value = "scope", defaultValue = "profile") String scope,
                               @RequestHeader(value = SecurityConstants.REQUEST_HEADER_AUTHORIZATION) String authorization);

    @PostMapping("/security/authentication/refresh_token")
    @Operation(summary = "刷新 Token")
    R<OAuth2Token> refreshToken(@RequestParam String refreshToken,
                                @RequestHeader(value = SecurityConstants.REQUEST_HEADER_AUTHORIZATION) String authorization);

}
