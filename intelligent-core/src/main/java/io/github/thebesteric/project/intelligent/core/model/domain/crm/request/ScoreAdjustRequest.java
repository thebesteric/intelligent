package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.IdempotentKey;
import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.core.constant.crm.ScoreType;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Score;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;

/**
 * ScoreAdjustRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-16 13:52:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreAdjustRequest extends BaseRequest<Score> {
    @Serial
    private static final long serialVersionUID = -1120509394309561650L;

    @IdempotentKey
    @Schema(description = "客户 IDs", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "客户 ID 不能为空")
    @Size(min = 1, max = 100, message = "客户 ID 不能超过 {max} 个")
    private List<Long> customerIds;

    @IdempotentKey
    @Schema(description = "积分类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private ScoreType scoreType;

    @IdempotentKey
    @Schema(description = "调整积分", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "调整积分不能为空")
    @Range(min = 1, max = 1000, message = "积分调整范围在 {min} 和 {max} 之间")
    private BigDecimal changeScore;

    @Schema(description = "调整原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "调整原因不能为空")
    @Length(min = 1, max = 500, message = "调整原因不能超过 {max} 个字符")
    private String desc;

}
