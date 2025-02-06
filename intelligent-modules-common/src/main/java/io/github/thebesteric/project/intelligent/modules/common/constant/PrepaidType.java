package io.github.thebesteric.project.intelligent.modules.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.thebesteric.framework.agile.core.domain.BaseCodeDescEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 预存款类型
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 17:22:27
 */
@Getter
@AllArgsConstructor
public enum PrepaidType implements BaseCodeDescEnum {
    ADD("ADD", "增加"),
    SUB("SUB", "减少");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static PrepaidType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}