package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户等级-更新
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 18:51:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelUpdateRequest extends CustomerLevelCreateRequest {
    @Serial
    private static final long serialVersionUID = 8449671997978365041L;

    @Schema(description = "ID")
    @NotNull(message = "ID 不能为空")
    private Long id;
}
