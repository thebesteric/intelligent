package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.response.WarehouseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * WarehousePurchaseAuthInfoResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 16:30:42
 */
@Data
public class WarehousePurchaseAuthInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -4820252651071838289L;

    @Schema(description = "仓库信息")
    private WarehouseResponse warehouse;

    @Schema(description = "是否授权")
    private boolean purchaseAuth = false;
}
