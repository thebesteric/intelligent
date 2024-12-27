package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerRelationAlarmCreateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-25 15:46:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRelationAlarmUpdateRequest extends CustomerRelationAlarmCreateRequest {
    @Serial
    private static final long serialVersionUID = 3864332496181957935L;

    @Schema(description = "ID")
    @NotNull(message = "ID 不能为空")
    private Long id;
}
