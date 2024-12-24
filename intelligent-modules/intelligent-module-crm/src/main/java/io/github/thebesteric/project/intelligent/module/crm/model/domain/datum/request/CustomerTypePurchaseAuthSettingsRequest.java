package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTypePurchaseAuth;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * CustomerTypePurchaseAuthSettingsRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 09:41:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTypePurchaseAuthSettingsRequest extends BaseRequest<CustomerTypePurchaseAuth> {
    @Serial
    private static final long serialVersionUID = 1167141200553472054L;

    @Schema(description = "客户类型 ID")
    @NotNull(message = "客户类型 ID 不能为空")
    private Long customerTypeId;

    @Schema(description = "品牌 IDs")
    private List<String> brandIds;

    @Schema(description = "目录 IDs")
    private List<String> categoryIds;

    @Schema(description = "仓库 IDs")
    private List<String> warehouseIds;

    @Schema(description = "标签 IDs")
    private List<String> tagIds;

    @Schema(description = "可购商品 IDs")
    private List<String> enableProductIds;

    @Schema(description = "禁用商品 IDs")
    private List<String> disableProductIds;
}
