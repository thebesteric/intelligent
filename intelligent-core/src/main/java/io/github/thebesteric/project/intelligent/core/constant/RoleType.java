package io.github.thebesteric.project.intelligent.core.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {

    SYSTEM("S", "系统角色"),
    CUSTOM("C", "自定义角色");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static RoleType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
