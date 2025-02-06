package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.IdempotentKey;
import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.ScoreSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 积分设置更新请求
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-21 15:18:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreSettingsUpdateRequest extends BaseRequest<ScoreSettings> {
    @Serial
    private static final long serialVersionUID = -1153093163422736162L;

    @IdempotentKey
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "进货积分规则")
    private ScoreSettings.PurchaseScoreRules purchaseScoreRules;

    @Schema(description = "其他积分规则")
    private ScoreSettings.OtherScoreRules otherScoreRules;

    @Schema(description = "积分抵扣现金设置")
    private ScoreSettings.ScoreDeductionCashSettings scoreDeductionCashSettings;

    @Schema(description = "积分抵扣运费设置")
    private ScoreSettings.ScoreDeductionFreightSettings scoreDeductionFreightSettings;
}
