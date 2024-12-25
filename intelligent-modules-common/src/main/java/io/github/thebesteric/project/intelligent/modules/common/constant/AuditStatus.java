package io.github.thebesteric.project.intelligent.modules.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * AuditStatus
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 17:37:42
 */
@Getter
@AllArgsConstructor
public enum AuditStatus {
    WAIT_AUDIT(0, "待审核"),
    AUDIT_PASS(1, "审核通过"),
    AUDIT_REJECT(2, "审核拒绝");

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

    public static AuditStatus of(Integer code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }
}
