package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.module.crm.constant.DiscountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 折扣信息设置
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 16:03:41
 */
@Data
public class CustomerDiscountInfoSettingsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 353038929599487804L;

    @Schema(description = "会员等级 ID")
    @NotNull(message = "会员等级 ID 不能为空")
    private Long customerLevelId;

    @Schema(description = "折扣类型")
    @NotNull(message = "折扣类型不能为空")
    private DiscountType discountType;

    @Schema(description = "折扣信息")
    @Valid
    private List<DiscountInfo> discountInfos;

    @Data
    public static class DiscountInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 3401551125728919871L;

        @Schema(description = "折扣对象 ID")
        @NotNull(message = "折扣对象 ID 不能为空")
        private Long discountObjectId;

        @Schema(description = "折扣率")
        @NotNull(message = "折扣率不能为空")
        @DecimalMin(value = "0.01", message = "折扣率不能小于 {value}")
        @DecimalMax(value = "1.00", message = "折扣率不能大于 {value}")
        private Double discountRate = 1.0D;
    }
}
