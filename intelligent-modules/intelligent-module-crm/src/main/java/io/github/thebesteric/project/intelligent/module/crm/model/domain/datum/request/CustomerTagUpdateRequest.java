package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerTagUpdateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 15:43:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTagUpdateRequest extends CustomerTagCreateRequest {
    @Serial
    private static final long serialVersionUID = 546566451936273064L;

    @Schema(description = "ID")
    @NotNull(message = "ID 不能为空")
    private Long id;
}
