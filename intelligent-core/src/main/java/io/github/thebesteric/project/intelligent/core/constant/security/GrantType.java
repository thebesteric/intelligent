package io.github.thebesteric.project.intelligent.core.constant.security;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * 授权类型
 *
 * @author wangweijun
 * @since 2024/6/17 18:45
 */
@Getter
public enum GrantType {

    GRANT_TYPE_PASSWORD("authorization_password", "密码模式"),
    GRANT_TYPE_CODE("authorization_code", "授权码模式"),
    GRANT_TYPE_CLIENT_CREDENTIALS("client_credentials", "客户端模式"),
    GRANT_TYPE_REFRESH_TOKEN("refresh_token", "刷新令牌");

    @EnumValue
    @JsonValue
    private final String type;
    private final String desc;

    GrantType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    @JsonCreator
    public static GrantType ofType(String type) {
        return Arrays.stream(GrantType.values()).filter(i -> ObjectUtil.equals(i.getType(), type)).findFirst().orElse(null);
    }
}
