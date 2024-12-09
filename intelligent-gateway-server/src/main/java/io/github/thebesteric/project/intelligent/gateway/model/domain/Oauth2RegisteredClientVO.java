package io.github.thebesteric.project.intelligent.gateway.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Client 注册的 VO
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 17:38:48
 */
@Data
public class Oauth2RegisteredClientVO {
    /** 客户端名称 */
    private String clientName;
    /** 客户端 ID */
    private String clientId;
    /** 客户端密码（原始密码） */
    private String clientSecret;
    /** 回调地址 */
    private List<String> redirectUris;
    /** 注销地址 */
    private List<String> postLogoutRedirectUris;
    /** 权限范围 */
    private List<String> scopes;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date clientIdIssuedAt;
    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date clientSecretExpiresAt;
    /** 是否包含密码模式*/
    private boolean includeGrantTypePassword;
    /** 是否包含 OpenId 权限范围 */
    private boolean includeOpenIdScope;
}
