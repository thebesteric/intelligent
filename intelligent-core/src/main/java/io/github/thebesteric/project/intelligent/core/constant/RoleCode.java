package io.github.thebesteric.project.intelligent.core.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum RoleCode {

    SUPER_ADMIN(ApplicationConstants.SYSTEM_TENANT_ID, "超级管理员", "super:admin", RoleType.SYSTEM);

    private final String tenantId;
    private final String name;
    @EnumValue
    @JsonValue
    private final String code;
    private final RoleType type;

    public static RoleCode of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

    public static List<RoleCode> list() {
        return Arrays.stream(values()).toList();
    }

}
