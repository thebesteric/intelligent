package io.github.thebesteric.project.intelligent.core.constant.crm;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * RegisterChannel
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-25 10:35:16
 */
@Getter
@AllArgsConstructor
public enum RegisterSource {

    INNER("INNER", "内部渠道"),
    OUTER("OUTER", "外部渠道");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static RegisterSource of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}
