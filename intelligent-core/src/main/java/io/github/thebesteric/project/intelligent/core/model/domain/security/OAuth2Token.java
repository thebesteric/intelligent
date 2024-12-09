package io.github.thebesteric.project.intelligent.core.model.domain.security;

import cn.hutool.json.JSONUtil;
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

    private String accessToken;
    private String refreshToken;
    private String idToken;
    private String scope;
    private String tokenType;

    public static OAuth2Token of(String jsonStr) {
        return JSONUtil.toBean(jsonStr, OAuth2Token.class);
    }
}
