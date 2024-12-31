package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTagGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class CustomerTagGroupCreateRequest extends BaseRequest<CustomerTagGroup> {
    @Serial
    private static final long serialVersionUID = -3774579863917261527L;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 64, message = "名称长度范围在 {min} 和 {max} 之间")
    private String name;
}
