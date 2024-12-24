package io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * ProductTagResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 16:57:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductTagResponse extends BaseResponse<ProductTag> {
    @Serial
    private static final long serialVersionUID = -70809027350403498L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "是否在商城前端显示")
    private boolean displayInFront = true;

    @Schema(description = "状态：0-禁用，1-启用")
    protected Integer state;
}
