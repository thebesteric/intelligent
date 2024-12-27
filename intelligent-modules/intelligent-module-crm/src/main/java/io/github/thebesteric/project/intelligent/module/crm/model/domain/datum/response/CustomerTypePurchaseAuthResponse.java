package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTypePurchaseAuth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 客户类型采购授权响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 11:21:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTypePurchaseAuthResponse extends BaseResponse<CustomerTypePurchaseAuth> {
    @Serial
    private static final long serialVersionUID = -6382572276592533929L;

    @Schema(description = "客户类型 ID")
    private Long customerTypeId;

    @Schema(description = "品牌信息")
    private List<BrandPurchaseAuthInfoResponse> brandInfos;

    @Schema(description = "目录信息")
    private List<CatalogPurchaseAuthInfoResponse> categoryInfos;

    @Schema(description = "仓库信息")
    private List<WarehousePurchaseAuthInfoResponse> warehouseInfos;

    @Schema(description = "标签信息")
    private List<TagPurchaseAuthInfoResponse> tagInfos;

    @Schema(description = "可购商品 IDs")
    private List<String> enableProductIds;

    @Schema(description = "禁用商品 IDs")
    private List<String> disableProductIds;
}
