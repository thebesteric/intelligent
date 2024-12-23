package io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * BrandUpdateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-20 11:46:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductBrandUpdateRequest extends ProductBrandCreateRequest {
    @Serial
    private static final long serialVersionUID = 8556977168999500526L;

    @Schema(description = "ID")
    @NotNull(message = "ID 不能为空")
    private Long id;
}
