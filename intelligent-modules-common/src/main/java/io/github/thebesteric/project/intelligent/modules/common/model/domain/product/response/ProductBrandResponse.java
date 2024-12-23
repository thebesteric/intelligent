package io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductBrand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * BrandResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 20:50:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductBrandResponse extends BaseResponse<ProductBrand> {
    @Serial
    private static final long serialVersionUID = -8017200219145263481L;

    @Schema(description = "全名")
    private String name;

    @Schema(description = "别名")
    private List<String> alias;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "官网")
    private String website;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "图标 ID")
    private Long iconImageId;

    @Schema(description = "状态：0-禁用，1-启用")
    protected Integer state;

}
