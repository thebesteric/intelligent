package io.github.thebesteric.project.intelligent.core.constant.crm;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IdentityType {

    MANAGER("MANAGER", "管理人员"),
    OPERATOR("OPERATOR", "操作人员"),
    SALES("SALES", "业务员");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static IdentityType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
