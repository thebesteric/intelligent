package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * CustomerTagCreateResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 13:17:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTagCreateRequest extends BaseRequest<CustomerTag> {
    @Serial
    private static final long serialVersionUID = 8503113859162370468L;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 64, message = "名称长度范围在 {min} 和 {max} 之间")
    private String name;

    @Schema(description = "标签组 ID")
    @NotNull(message = "标签组 ID 不能为空")
    private Long groupId;
}
