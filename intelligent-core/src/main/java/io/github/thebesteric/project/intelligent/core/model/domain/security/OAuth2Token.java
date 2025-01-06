package io.github.thebesteric.project.intelligent.core.model.domain.security;

import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * OAuth2Token
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-05-24 17:04:41
 */
@Data
public class OAuth2Token implements Serializable {
    @Serial
    private static final long serialVersionUID = 8265493360045452338L;

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "身份令牌")
    private String idToken;

    @Schema(description = "授权范围")
    private String scope;

    @Schema(description = "令牌类型")
    private String tokenType;

    public static OAuth2Token of(String jsonStr) {
        return JSONUtil.toBean(jsonStr, OAuth2Token.class);
    }
}
