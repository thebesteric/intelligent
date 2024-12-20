package io.github.thebesteric.project.intelligent.modules.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Unit {

    FEN("F", "分"),
    YUAN("Y", "元"),
    OTHER("O", "其他");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static Unit of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
