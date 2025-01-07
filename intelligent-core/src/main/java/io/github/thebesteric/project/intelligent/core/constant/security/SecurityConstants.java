package io.github.thebesteric.project.intelligent.core.constant.security;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

/**
 * SecurityConstants
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-23 21:14:54
 */
public final class SecurityConstants extends AbstractUtils {

    /** 认证请求头 */
    public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";
    /** 忽略请求头 */
    public static final String REQUEST_HEADER_SKIP_AUTHORIZATION = "Skip-Authorization";
    /** JWK Set 缓存前缀 */
    public static final String AUTHORIZATION_JWK_SET_KEY = "authorization_jwk_set";
    /** 授权模式 */
    public static final String GRANT_TYPE = "grant_type";
    /** 刷新令牌 */
    public static final String REFRESH_TOKEN = "refresh_token";
    /** 认证模式 */
    public static final String AUTH_TYPE = "auth_type";
    /** 登录设备类型，PC、APP、H5、MP (小程序) */
    public static final String DEVICE_TYPE = "device_type";
    /** 认证源 */
    public static final String AUTH_SOURCE = "auth_source";
    /** CLAIM 设置 */
    public static final String CLAIM_SID = "sid";
    public static final String CLAIM_AUTHORITIES = "authorities";
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_SCOPE = "scope";
    public static final String CLAIM_ISSUER = "iss";
    public static final String CLAIM_ISSUED_AT = "iat";
    public static final String CLAIM_EXPIRE = "exp";
    public static final String CLAIM_SUBJECT = "sub";
    public static final String CLAIM_AUDIENCE = "aud";
    public static final String CLAIM_NOT_BEFORE = "nbf";
    public static final String CLAIM_IDENTITY = "identity";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_TENANT_ID = "tenant_id";
    public static final String CLAIM_AUTH_TYPE = "auth_type";
    public static final String CLAIM_AUTH_SOURCE = "auth_source";
    public static final String CLAIM_EXTRA = "extra";

    /** 授权范围 */
    public static final class Scope extends AbstractUtils {
        /** 支持 OpenID Connect 模式 */
        public static final String OPENID = "openid";
        /** profile 提供用户权限验证 */
        public static final String PROFILE = "profile";
    }

    /** 认证异常 */
    public static final class OAuth2ErrorCode extends AbstractUtils {
        private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

        public static final OAuth2Error GRANT_TYPE_UNAUTHORIZED = new OAuth2Error(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, "认证方式未授权", ERROR_URI);
        public static final OAuth2Error INVALID_USERNAME = new OAuth2Error(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, "用户名不正确", ERROR_URI);
        public static final OAuth2Error INVALID_PASSWORD = new OAuth2Error(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, "密码不正确", ERROR_URI);
        public static final OAuth2Error INVALID_CLIENT =  new OAuth2Error(OAuth2ErrorCodes.INVALID_CLIENT, "客户端身份验证失败", ERROR_URI);
        public static final OAuth2Error GENERATE_ACCESS_TOKEN_FAILURE =  new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "访问令牌生成失败", ERROR_URI);
        public static final OAuth2Error GENERATE_REFRESH_TOKEN_FAILURE =  new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "刷新令牌生成失败", ERROR_URI);
        public static final OAuth2Error GENERATE_ID_TOKEN_FAILURE =  new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "身份令牌生成失败", ERROR_URI);
    }
}
