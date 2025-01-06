package io.github.thebesteric.project.intelligent.core.constant.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PrivilegeType {

    CATALOG("C", "虚拟目录"),
    MENU("M", "菜单权限"),
    OPERATION("OP", "操作权限"),
    DATA("D", "数据权限");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static PrivilegeType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
