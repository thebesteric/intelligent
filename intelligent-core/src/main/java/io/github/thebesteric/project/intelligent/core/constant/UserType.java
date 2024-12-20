package io.github.thebesteric.project.intelligent.core.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {

    SUPER_ADMIN("S", "超级管理员"),
    ADMIN("A", "管理员"),
    MEMBER("M", "普通用户");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static UserType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
