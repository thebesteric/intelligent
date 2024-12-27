package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 品牌采购授权信息响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 11:57:48
 */
@Data
public class BrandPurchaseAuthInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 5132436022859527597L;

    @Schema(description = "品牌信息")
    private ProductBrandResponse brand;

    @Schema(description = "是否授权")
    private boolean purchaseAuth = false;
}
