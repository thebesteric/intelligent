package io.github.thebesteric.project.intelligent.core.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum NodeType {

    PARENT("P", "父节点"),
    CHILD("C", "子节点"),
    VIRTUAL("V", "虚拟节点");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static NodeType of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

}
