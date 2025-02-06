package io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidType;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.crm.Prepaid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 预存款调整
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-02-05 10:04:43
 */
@Data
public class PrepaidAdjustRequest extends BaseRequest<Prepaid> {
    @Serial
    private static final long serialVersionUID = 3829087948095279376L;

    @Schema(description = "客户 ID")
    @NotNull(message = "客户 ID不能为空")
    private Long customerId;

    @Schema(description = "调整类型")
    @NotNull(message = "调整类型不能为空")
    private PrepaidType type;

    @Schema(description = "调整金额")
    @Range(min = 1, max = 10000, message = "调整范围在 {min} 和 {max} 之间")
    private BigDecimal changeAmount;

    @Schema(description = "调整原因")
    @NotBlank(message = "调整原因不能为空")
    private String desc;

}
