package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;

/**
 * 客户等级-新增
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 18:51:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelCreateRequest extends BaseRequest<CustomerLevel> {
    @Serial
    private static final long serialVersionUID = -1117871534574805116L;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "上一个等级")
    private Long preLevelId;

    @Schema(description = "折扣率")
    @NotNull(message = "折扣率不能为空")
    @DecimalMin(value = "0.01", message = "折扣率不能小于 {value}")
    @DecimalMax(value = "1.00", message = "折扣率不能大于 {value}")
    private Double discountRate = 1.0D;

    @Schema(description = "是否默认")
    private Boolean isDefault = false;

    @Schema(description = "自动升级")
    private Boolean autoUpgrade = false;

    @Schema(description = "升级积分")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "升级积分不能小于 {min}")
    private Integer upgradeScore;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    private Integer state = 1;
}
