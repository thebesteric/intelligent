package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 目录折扣信息
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 10:55:00
 */
@Data
public class CatalogDiscountInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -8237649984802562898L;

    @Schema(description = "目录信息")
    private ProductCatalogResponse catalogResponse;

    @Schema(description = "折扣信息")
    private CustomerDiscountInfoResponse discountInfoResponse;

    @Schema(description = "子目录")
    private List<CatalogDiscountInfoResponse> children;
}
