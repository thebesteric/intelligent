package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.constant.DiscountType;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerDiscountInfo;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * BrandDiscountInfo
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-20 16:34:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerDiscountInfoResponse extends BaseResponse<CustomerDiscountInfo> {
    @Serial
    private static final long serialVersionUID = 3070079681056479705L;

    @Schema(description = "品牌信息")
    private ProductBrandResponse brandResponse;

    @Schema(description = "会员等级 ID")
    private Long customerLevelId;

    @Schema(description = "折扣类型")
    private DiscountType discountType;

    @Schema(description = "折扣对象 ID")
    private Long discountObjectId;

    @Schema(description = "折扣率")
    private Double discountRate;
}
