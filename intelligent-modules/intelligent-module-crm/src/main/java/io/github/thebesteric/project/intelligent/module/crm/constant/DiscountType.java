package io.github.thebesteric.project.intelligent.module.crm.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * DiscuontType
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 20:53:15
 */
@Getter
@AllArgsConstructor
public enum DiscountType {

    BRAND("B", "按品牌打折"),
    CATEGORY("C", "按目录打折");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static DiscountType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}
