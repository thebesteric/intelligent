package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 客户区域-添加/移除区域关联用户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-31 17:37:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegionUserUpsertRequest extends BaseRequest<Customer> {
    @Serial
    private static final long serialVersionUID = 2515467447144435348L;

    @Schema(description = "区域 ID")
    @NotNull(message = "区域 ID 不能为空")
    private Long regionId;

    @Schema(description = "客户 IDs")
    @NotEmpty(message = "客户 IDs 不能为空")
    private List<Long> customerIds;
}
