package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTagGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户标签组响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-18 15:51:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTagGroupResponse extends BaseResponse<CustomerTagGroup> {
    @Serial
    private static final long serialVersionUID = -2685969089532535586L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "包含标签的数量")
    private Integer count;

}