package io.github.thebesteric.project.intelligent.core.constant.crm;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.thebesteric.framework.agile.core.domain.BaseCodeDescEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ConsumeType implements BaseCodeDescEnum {
    CHECK_IN("CHECK_IN", "签到"),
    MANUAL_ADJUST("MANUAL_ADJUST", "手工调整"),
    ;

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static ConsumeType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }
}
