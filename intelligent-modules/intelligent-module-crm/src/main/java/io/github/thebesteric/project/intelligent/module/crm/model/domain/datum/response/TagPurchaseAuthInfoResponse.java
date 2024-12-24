package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductTagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * TagPurchaseAuthInfoResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 16:55:56
 */
@Data
public class TagPurchaseAuthInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 15602276334904467L;

    @Schema(description = "标签信息")
    private ProductTagResponse tag;

    @Schema(description = "是否授权")
    private boolean purchaseAuth = false;
}
