package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerTypeUpdateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 19:48:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTypeUpdateRequest extends CustomerTypeCreateRequest{
    @Serial
    private static final long serialVersionUID = 1716889941970389744L;

    @Schema(description = "ID")
    @NotNull(message = "ID 不能为空")
    private Long id;
}
