package io.github.thebesteric.project.intelligent.module.open.api.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.thebesteric.framework.agile.commons.exception.InvalidParamsException;
import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.annotation.SkipAuth;
import io.github.thebesteric.project.intelligent.core.constant.security.*;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.model.domain.security.OAuth2Token;
import io.github.thebesteric.project.intelligent.core.model.domain.security.request.OAuth2TokenRequest;
import io.github.thebesteric.project.intelligent.core.properties.ApplicationProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * AuthenticationController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 20:19:51
 */
@Slf4j
@AgileLogger
@RestController
@RequestMapping("/security/authentication")
@RequiredArgsConstructor
@Tag(name = "安全-认证相关")
@SkipAuth
public class SecurityAuthenticationController {

    private final ApplicationProperties applicationProperties;

    /**
     * 获取令牌
     *
     * @param username      用户名
     * @param password      密码
     * @param grantType     授权类型
     * @param authType      认证模式
     * @param authSource    认证源
     * @param scope         授权范围，多个用空格分隔
     * @param authorization 授权信息
     *
     * @return R<OAuth2Token>
     *
     * @author wangweijun
     * @since 2024/7/25 16:21
     */
    @PostMapping("/access_token")
    @Operation(summary = "获取令牌")
    @Parameter(name = "username", description = "用户名", required = true)
    @Parameter(name = "password", description = "密码", required = true)
    @Parameter(name = "grant_type", description = "授权类型")
    @Parameter(name = "auth_type", description = "认证模式")
    @Parameter(name = "auth_source", description = "认证源")
    @Parameter(name = "scope", description = "授权范围")
    public R<OAuth2Token> accessToken(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam(value = "grant_type", defaultValue = "authorization_password") String grantType,
                                      @RequestParam(value = "auth_type", defaultValue = "password") String authType,
                                      @RequestParam(value = "auth_source", defaultValue = "intelligent-core-api") String authSource,
                                      @RequestParam(value = "scope", defaultValue = "profile") String scope,
                                      @NotBlank(message = "请求认证头不能为空") @RequestParam(value = SecurityConstants.REQUEST_HEADER_AUTHORIZATION, required = false) String authorization) {
        OAuth2TokenRequest tokenRequest = OAuth2TokenRequest.of(username, password, GrantType.ofType(grantType), AuthType.ofType(authType), AuthSource.ofSource(authSource), Scope.to(scope));
        ApplicationProperties.OAuth2 oAuth2 = applicationProperties.getOauth2();
        String tokenUri = oAuth2.getAccessTokenUri(tokenRequest);
        return sendRequest(tokenUri, authorization, null);
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken  刷新令牌
     * @param authorization 授权信息
     *
     * @return R<OAuth2Token>
     *
     * @author wangweijun
     * @since 2024/6/17 20:07
     */
    @PostMapping("/refresh_token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    public R<OAuth2Token> refreshToken(@RequestParam String refreshToken,
                                       @RequestParam(value = SecurityConstants.REQUEST_HEADER_AUTHORIZATION, required = false) String authorization) {
        ApplicationProperties.OAuth2 oAuth2 = applicationProperties.getOauth2();
        String refreshTokenUri = oAuth2.getRefreshTokenUri();
        Map<String, Object> formParams = Map.of(
                SecurityConstants.GRANT_TYPE, GrantType.GRANT_TYPE_REFRESH_TOKEN.getType(),
                SecurityConstants.REFRESH_TOKEN, refreshToken
        );
        return sendRequest(refreshTokenUri, authorization, formParams);
    }


    /**
     * 发送请求
     *
     * @param tokenUri      请求地址
     * @param authorization 授权信息
     *
     * @return R<OAuth2Token>
     *
     * @author wangweijun
     * @since 2024/6/17 20:07
     */
    private R<OAuth2Token> sendRequest(String tokenUri, String authorization, Map<String, Object> formParams) {
        if (CharSequenceUtil.isEmpty(authorization)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            // 从请求头中获取授权信息
            authorization = request.getHeader(SecurityConstants.REQUEST_HEADER_AUTHORIZATION);
            if (CharSequenceUtil.isEmpty(authorization)) {
                throw new InvalidParamsException("Authorization header is required");
            }
        }

        HttpRequest httpRequest = HttpUtil.createPost(tokenUri).setConnectionTimeout(5000).setReadTimeout(5000);
        if (formParams != null) {
            httpRequest.form(formParams);
        }
        httpRequest.header(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, authorization);

        try (HttpResponse response = httpRequest.execute()) {
            String result = response.body();
            if (response.isOk()) {
                return R.success(OAuth2Token.of(result));
            }
            JSONObject resultObj = JSONUtil.parseObj(result);
            R<OAuth2Token> error = R.error();
            error.setCode(resultObj.getInt("code"));
            error.setMessage(resultObj.getStr("message"));
            error.setTrackId(resultObj.getStr("trackId"));
            return error;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizException(e.getMessage());
        }
    }
}
