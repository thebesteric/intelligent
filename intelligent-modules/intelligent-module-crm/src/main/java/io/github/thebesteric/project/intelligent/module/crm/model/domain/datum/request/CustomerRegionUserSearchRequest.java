package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerRegionUserSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 18:11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegionUserSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = -2351394997599280988L;

    @Schema(description = "编号/名称")
    private String keyword;

    @Schema(description = "客户等级 ID")
    private Long customerLevelId;

    @Schema(description = "客户类型 ID")
    private Long customerTypeId;

    @Schema(description = "关联业务员 ID")
    private String salesUserId;
}
