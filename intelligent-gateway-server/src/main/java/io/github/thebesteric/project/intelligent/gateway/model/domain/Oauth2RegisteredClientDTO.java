package io.github.thebesteric.project.intelligent.gateway.model.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * Client 注册的 DTO
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 16:37:58
 */
@Data
public class Oauth2RegisteredClientDTO {

    @NotBlank
    /** 客户端名称 */
    private String clientName;

    @NotEmpty
    /** 回调地址 */
    private List<String> redirectUris;

    @NotEmpty
    /** 注销地址 */
    private List<String> postLogoutRedirectUris;

    /** 是否包括密码授权类型 */
    private boolean includeGrantTypePassword;

    /** 是否包括 OpenId 权限范围 */
    private boolean includeOpenIdScope;
}
