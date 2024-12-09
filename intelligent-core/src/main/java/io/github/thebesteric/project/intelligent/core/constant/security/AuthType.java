package io.github.thebesteric.project.intelligent.core.constant.security;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * 认证模式
 *
 * @author wangweijun
 * @since 2024/5/22 13:30
 */
@Getter
public enum AuthType {

    PASSWORD("password", "密码模式"),
    CODE("code", "验证码模式"),
    UNSAFE("unsafe", "账号模式");

    @EnumValue
    @JsonValue
    private final String type;
    private final String desc;

    AuthType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    @JsonCreator
    public static AuthType ofType(String type) {
        return Arrays.stream(AuthType.values()).filter(i -> ObjectUtil.equals(i.getType(), type)).findFirst().orElse(null);
    }
}