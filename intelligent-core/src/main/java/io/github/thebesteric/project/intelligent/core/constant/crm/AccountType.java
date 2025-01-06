package io.github.thebesteric.project.intelligent.core.constant.crm;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 账号类型
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 18:16:40
 */
@Getter
@AllArgsConstructor
public enum AccountType {

    MASTER("MASTER", "主账号"),
    SLAVE("SLAVE", "子账号");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static AccountType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}
