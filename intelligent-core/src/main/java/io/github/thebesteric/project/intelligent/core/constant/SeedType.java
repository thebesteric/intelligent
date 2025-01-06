package io.github.thebesteric.project.intelligent.core.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * SeedType
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 11:39:34
 */
@Getter
@AllArgsConstructor
public enum SeedType {

    CUSTOMER_SERIAL_NO("CUSTOMER_SERIAL_NO", "CSN", 11, "客户编号"),
    CUSTOMER_REGION_CODE("CUSTOMER_REGION_CODE", "RGN", 5, "客户区域")
    ;

    @EnumValue
    @JsonValue
    private final String code;
    private final String prefix;
    private final Integer digits;
    private final String name;

    public static SeedType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
