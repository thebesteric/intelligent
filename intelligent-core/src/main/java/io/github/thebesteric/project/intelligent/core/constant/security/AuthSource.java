package io.github.thebesteric.project.intelligent.core.constant.security;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * 认证源
 *
 * @author wangweijun
 * @since 2024/5/22 14:51
 */
@Getter
public enum AuthSource {

    PMS("pms", "PMS 平台"),
    SERVICE_MALL("service-mall", "商城服务"),
    PLATFORM("platform", "中台认证平台");

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
        return Arrays.stream(AuthSource.values()).filter(i -> ObjectUtil.equals(i.getSource(), source)).findFirst().orElse(null);
    }
}