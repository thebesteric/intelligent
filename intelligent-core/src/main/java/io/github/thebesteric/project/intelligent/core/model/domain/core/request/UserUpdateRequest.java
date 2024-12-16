package io.github.thebesteric.project.intelligent.core.model.domain.core.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户更新请求
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:55:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateRequest extends UserCreateRequest {
    @Serial
    private static final long serialVersionUID = 6456925185417184191L;

    @Schema(description = "主键")
    private String id;

}
