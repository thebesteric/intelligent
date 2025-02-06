package io.github.thebesteric.project.intelligent.modules.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.thebesteric.framework.agile.core.domain.BaseCodeDescEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * PrepaidProject
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 17:12:52
 */
@Getter
@AllArgsConstructor
public enum PrepaidChangeProject implements BaseCodeDescEnum {

    SHOPPING("SHOPPING", "购物减去预存款"),
    AUTO_RECHARGE("AUTO_RECHARGE", "自助充值"),
    MANUAL_ADJUST("MANUAL_ADJUST", "手工调整"),
    ;

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static PrepaidChangeProject of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}
