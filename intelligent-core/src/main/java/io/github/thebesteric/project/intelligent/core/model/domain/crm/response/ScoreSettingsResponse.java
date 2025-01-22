package io.github.thebesteric.project.intelligent.core.model.domain.crm.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.ScoreSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * ScoreSettingsResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-18 09:42:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreSettingsResponse extends BaseResponse<ScoreSettings> {
    @Serial
    private static final long serialVersionUID = 3047693979629528162L;

    @Schema(description = "进货积分规则")
    private ScoreSettings.PurchaseScoreRules purchaseScoreRules;

    @Schema(description = "其他积分规则")
    private ScoreSettings.OtherScoreRules otherScoreRules ;

    @Schema(description = "积分抵扣现金设置")
    private ScoreSettings.ScoreDeductionCashSettings scoreDeductionCashSettings;

    @Schema(description = "积分抵扣运费设置")
    private ScoreSettings.ScoreDeductionFreightSettings scoreDeductionFreightSettings;

}
