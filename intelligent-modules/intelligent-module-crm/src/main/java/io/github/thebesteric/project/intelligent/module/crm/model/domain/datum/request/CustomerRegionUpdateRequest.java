package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户区域-更新
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 14:46:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegionUpdateRequest extends CustomerRegionCreateRequest {
    @Serial
    private static final long serialVersionUID = 5716614702392737661L;

    @Schema(description = "ID")
    @NotNull(message = "ID 不能为空")
    private Long id;
}
