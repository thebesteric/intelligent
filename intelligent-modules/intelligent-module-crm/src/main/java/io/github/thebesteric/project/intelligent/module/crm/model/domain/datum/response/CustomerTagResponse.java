package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户标签响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-18 15:51:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTagResponse extends BaseResponse<CustomerTag> {
    @Serial
    private static final long serialVersionUID = 3685165245016145669L;

    @Schema(description = "标签名称")
    private String name;

    @Schema(description = "标签组 ID")
    private Long groupId;

    @Schema(description = "标签组名称")
    private String groupName;

}
