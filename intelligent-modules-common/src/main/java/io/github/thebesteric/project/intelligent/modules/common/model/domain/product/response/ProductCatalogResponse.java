package io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.Range;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductCatalog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * CatalogResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 11:50:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductCatalogResponse extends BaseResponse<ProductCatalog> {
    @Serial
    private static final long serialVersionUID = 4606134726740446612L;

    @Schema(description = "目录名称")
    private String name;

    @Schema(description = "排序")
    private Integer seq;

    @Schema(description = "父级目录")
    private Long parentId;

    @Schema(description = "图标 ID")
    private Long iconImageId;

    @Schema(description = "分类图 ID")
    private Long catalogImageId;

    @Schema(description = "价格区间，单位：分")
    private List<Range> priceRange;

    @Schema(description = "子目录")
    private List<ProductCatalogResponse> children = new ArrayList<>();
}
