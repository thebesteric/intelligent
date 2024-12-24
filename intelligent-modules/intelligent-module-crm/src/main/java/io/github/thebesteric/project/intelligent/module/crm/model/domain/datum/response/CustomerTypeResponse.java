package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerTypeResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 19:34:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTypeResponse extends BaseResponse<CustomerType> {
    @Serial
    private static final long serialVersionUID = 3574096007163336800L;

    @Schema(description = "客户类型名称")
    private String name;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "是否默认")
    private Boolean isDefault = false;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    private Integer state = 1;
}
