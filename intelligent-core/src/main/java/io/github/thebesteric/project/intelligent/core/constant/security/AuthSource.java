package io.github.thebesteric.project.intelligent.core.constant.security;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 认证源
 *
 * @author wangweijun
 * @since 2024/5/22 14:51
 */
@Getter
public enum AuthSource {

    MASTER("master", "平台-用户登录认证"),
    MODULE_CRM("crm", "模块-客户登录认证");

    @EnumValue
    @JsonValue
    private final String source;
    private final String desc;

    AuthSource(String source, String desc) {
        this.source = source;
        this.desc = desc;
    }

    @JsonCreator
    public static AuthSource ofSource(String source) {
        return Arrays.stream(AuthSource.values()).filter(i -> Objects.equals(i.getSource(), source)).findFirst().orElse(null);
    }
}