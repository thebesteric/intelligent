package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户等级响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-18 15:51:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelResponse extends BaseResponse<CustomerLevel> {
    @Serial
    private static final long serialVersionUID = -5382818443802990333L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "折扣率")
    private Double discountRate = 1.0D;

    @Schema(description = "是否默认")
    private Boolean isDefault = false;

    @Schema(description = "自动升级")
    private Boolean autoUpgrade = false;

    @Schema(description = "升级积分")
    private Integer upgradeScore;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    private Integer state = 1;

}
