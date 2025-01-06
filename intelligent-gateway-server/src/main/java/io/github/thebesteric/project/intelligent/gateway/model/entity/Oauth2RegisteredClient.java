package io.github.thebesteric.project.intelligent.gateway.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import io.github.thebesteric.project.intelligent.core.util.BCryptUtils;
import io.github.thebesteric.project.intelligent.core.util.IdentityUtils;
import lombok.Data;

import java.util.*;

/**
 * Oauth2RegisteredClient
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-19 22:47:24
 */
@Data
@TableName("oauth2_registered_client")
@EntityClass(value = "oauth2_registered_client", ignore = true)
public class Oauth2RegisteredClient {

    /** 密码模式 */
    public static final String GRANT_TYPE_PASSWORD = GrantType.GRANT_TYPE_PASSWORD.getType();

    /** 密码模式不支持 openid */
    public static final String SCOPE_OPENID = "openid";

    @TableId(type = IdType.ASSIGN_UUID)
    @EntityColumn(length = 100, primary = true, comment = "主键")
    private String id;

    @EntityColumn(length = 100, nullable = false, comment = "客户端 ID")
    private String clientId;

    @EntityColumn(length = 200, nullable = false, comment = "客户端名称")
    private String clientName;

    @EntityColumn(length = 200, nullable = false, comment = "客户端密码")
    private String clientSecret;

    @EntityColumn(type = EntityColumn.Type.DATETIME, nullable = false, comment = "创建日期")
    private Date clientIdIssuedAt = new Date();

    @EntityColumn(type = EntityColumn.Type.DATETIME, comment = "过期时间")
    private Date clientSecretExpiresAt;

    @EntityColumn(length = 1000, nullable = false, comment = "授权模式")
    private String clientAuthenticationMethods = defaultClientAuthenticationMethods();

    @EntityColumn(length = 1000, nullable = false, comment = "授权类型")
    private String authorizationGrantTypes = defaultAuthorizationGrantTypes();

    @EntityColumn(length = 1000, comment = "回调地址")
    private String redirectUris;

    @EntityColumn(length = 1000, comment = "注销地址")
    private String postLogoutRedirectUris;

    @EntityColumn(length = 1000, nullable = false, comment = "权限范围")
    private String scopes = defaultScopes();

    @EntityColumn(length = 2000, nullable = false, comment = "客户端设置")
    private String clientSettings = defaultClientSettings();

    @EntityColumn(length = 2000, nullable = false, comment = "令牌设置")
    private String tokenSettings = defaultTokenSettings();

    public String defaultClientAuthenticationMethods() {
        return "client_secret_basic";
    }

    public String defaultAuthorizationGrantTypes() {
        return GrantType.getTypesStr(GrantType.GRANT_TYPE_PASSWORD, GrantType.GRANT_TYPE_REFRESH_TOKEN);
    }

    public void setIdentity(IdentityUtils.Identity identity) {
        this.clientId = identity.getAppKey();
        this.clientSecret = BCryptUtils.encode(identity.getAppSecret());
    }

    public void appendPasswordAuthorizationGrantType() {
        String defaultAuthorizationGrantTypes = defaultAuthorizationGrantTypes();
        Set<String> set = new LinkedHashSet<>(List.of(defaultAuthorizationGrantTypes.split(",")));
        set.add(GRANT_TYPE_PASSWORD);
        this.authorizationGrantTypes = String.join(",", set);
    }

    public String defaultScopes() {
        return "profile";
    }

    public void appendOpenIdScope() {
        String defaultScopes = defaultScopes();
        Set<String> set = new LinkedHashSet<>(List.of(defaultScopes.split(",")));
        set.add(SCOPE_OPENID);
        this.scopes = String.join(",", set);
    }

    public boolean includeGrantTypePassword() {
        return this.authorizationGrantTypes.contains(GRANT_TYPE_PASSWORD);
    }

    public boolean includeOpenIdScope() {
        return this.scopes.contains(SCOPE_OPENID);
    }

    public String defaultClientSettings() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("@class", "java.util.Collections$UnmodifiableMap");
        params.put("settings.client.require-proof-key", false);
        params.put("settings.client.require-authorization-consent", true);
        return JsonUtils.toJson(params);
    }

    public String defaultTokenSettings() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("@class", "java.util.Collections$UnmodifiableMap");
        params.put("settings.token.reuse-refresh-tokens", true);
        params.put("settings.token.id-token-signature-algorithm", List.of("org.springframework.security.oauth2.jose.jws.SignatureAlgorithm", "RS256"));
        params.put("settings.token.access-token-time-to-live", List.of("java.time.Duration", 3600.000000000D));
        params.put("settings.token.access-token-format", Map.of("@class", "org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat", "value", "self-contained"));
        params.put("settings.token.refresh-token-time-to-live", List.of("java.time.Duration", 7200.000000000D));
        params.put("settings.token.authorization-code-time-to-live", List.of("java.time.Duration", 3600.000000000D));
        params.put("settings.token.device-code-time-to-live", List.of("java.time.Duration", 3600.000000000D));
        params.put("settings.token.x509-certificate-bound-access-tokens", false);
        return JsonUtils.toJson(params);
    }
}
