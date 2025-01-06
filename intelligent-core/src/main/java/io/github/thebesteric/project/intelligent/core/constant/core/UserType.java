package io.github.thebesteric.project.intelligent.core.constant.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {

    SUPER_ADMIN("SUPER", "超级管理员"),
    ADMIN("ADMIN", "管理员"),
    MEMBER("MEMBER", "普通用户");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static UserType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
