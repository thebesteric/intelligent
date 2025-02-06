package io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.project.intelligent.core.annotation.BigDecimalFormat;
import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.crm.Prepaid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * PrepaidDetailsResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-02-06 09:46:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PrepaidDetailsResponse extends BaseResponse<Prepaid> {
    @Serial
    private static final long serialVersionUID = 6969611762011921627L;

    @Schema(description = "变动类型")
    private Map<String, String> type;

    @Schema(description = "变动项目")
    private Map<String, String> changeProject;

    @Schema(description = "变动金额")
    @BigDecimalFormat
    private BigDecimal changeAmount = BigDecimal.ZERO;

    @Schema(description = "支付金额")
    @BigDecimalFormat
    private BigDecimal payAmount = BigDecimal.ZERO;

    @Schema(description = "奖励金额")
    @BigDecimalFormat
    private BigDecimal rewardAmount = BigDecimal.ZERO;

    @Schema(description = "可用余额")
    @BigDecimalFormat
    private BigDecimal balanceAmount = BigDecimal.ZERO;

    @Schema(description = "手续费")
    @BigDecimalFormat
    private BigDecimal feeAmount = BigDecimal.ZERO;

    @Schema(description = "变动时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Schema(description = "变动说明")
    private String desc;

}
