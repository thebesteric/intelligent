package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 品牌折扣信息
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-20 16:34:34
 */
@Data
public class BrandDiscountInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 6908954424907438267L;

    @Schema(description = "品牌信息")
    private ProductBrandResponse brandResponse;

    @Schema(description = "折扣信息")
    private CustomerDiscountInfoResponse discountInfoResponse;
}
