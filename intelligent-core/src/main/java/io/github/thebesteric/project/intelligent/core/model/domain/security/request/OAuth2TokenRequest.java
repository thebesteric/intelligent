package io.github.thebesteric.project.intelligent.core.model.domain.security.request;

import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import io.github.thebesteric.project.intelligent.core.constant.security.Scope;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OAuth2Token
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-05-24 17:04:41
 */
@Data
public class OAuth2TokenRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3991971598766442171L;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "授权类型不能为空")
    @Schema(name = "grant_type", description = "授权类型", enumAsRef = true)
    private String grantType = GrantType.GRANT_TYPE_PASSWORD.getType();

    @NotNull(message = "认证模式不能为空")
    @Schema(name = "auth_type", description = "认证模式")
    private String authType = AuthType.PASSWORD.getType();

    @NotNull(message = "认证源不能为空")
    @Schema(name = "auth_source", description = "认证源")
    private String authSource = AuthSource.PMS.getSource();

    @NotBlank(message = "授权范围不能为空")
    private String scope = "profile openid";

    public static OAuth2TokenRequest of(String username, String password) {
        OAuth2TokenRequest tokenRequest = new OAuth2TokenRequest();
        tokenRequest.username = username;
        tokenRequest.password = password;
        return tokenRequest;
    }

    public static OAuth2TokenRequest of(String username, String password, GrantType grantType, AuthType authType, AuthSource authSource, List<Scope> scopes) {
        OAuth2TokenRequest tokenRequest = of(username, password);
        tokenRequest.grantType = grantType.getType();
        tokenRequest.authType = authType.getType();
        tokenRequest.authSource = authSource.getSource();
        tokenRequest.scope = scopes.stream().map(Scope::getName).collect(Collectors.joining(" "));
        return tokenRequest;
    }
}
