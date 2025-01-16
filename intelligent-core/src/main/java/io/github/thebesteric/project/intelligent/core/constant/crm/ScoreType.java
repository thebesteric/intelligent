package io.github.thebesteric.project.intelligent.core.constant.crm;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.thebesteric.framework.agile.core.domain.BaseCodeDescEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 积分类型
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 19:25:27
 */
@Getter
@AllArgsConstructor
public enum ScoreType implements BaseCodeDescEnum {
    ADD("ADD", "增加"),
    SUB("SUB", "减少");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static ScoreType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}
