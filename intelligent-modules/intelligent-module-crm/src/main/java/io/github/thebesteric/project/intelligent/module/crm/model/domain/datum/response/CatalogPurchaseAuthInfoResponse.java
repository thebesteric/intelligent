package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CatalogPurchaseAuthInfoResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 12:44:56
 */
@Data
public class CatalogPurchaseAuthInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 7356088316790064451L;

    @Schema(description = "目录")
    private ProductCatalogResponse catalog;

    @Schema(description = "子目录")
    private List<CatalogPurchaseAuthInfoResponse> children = new ArrayList<>();

    @Schema(description = "是否授权")
    private boolean purchaseAuth = false;
}
