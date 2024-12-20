package io.github.thebesteric.project.intelligent.modules.common.model.domain;

import io.github.thebesteric.project.intelligent.modules.common.constant.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Range
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 14:51:32
 */
@Data
public class Range implements Serializable {
    @Serial
    private static final long serialVersionUID = -2923667187544278385L;

    @Schema(description = "最小值")
    private String min;

    @Schema(description = "最大值")
    private String max;

    @Schema(description = "单位")
    private Unit unit;
}
