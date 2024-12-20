package io.github.thebesteric.project.intelligent.core.constant.security;

/**
 * SecurityConstants
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-23 21:14:54
 */
public final class SecurityConstants {

    /** 认证请求头 */
    public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    /** 忽略请求头 */
    public static final String REQUEST_HEADER_SKIP_AUTHORIZATION = "Skip-Authorization";

    /** JWK Set 缓存前缀 */
    public static final String AUTHORIZATION_JWK_SET_KEY = "authorization_jwk_set";

    /** 认证模式 */
    public static final String AUTH_TYPE = "auth_type";
    /** 登录设备类型，PC、APP、H5、MP (小程序) */
    public static final String DEVICE_TYPE = "deviceType";

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
    public static final String CLAIM_EXTRA = "extra";

    /** 授权范围 */
    public static final class Scope {
        /** 支持 OpenID Connect 模式 */
        public static final String OPENID = "openid";

        /** profile 提供用户权限验证 */
        public static final String PROFILE = "profile";
    }
}
