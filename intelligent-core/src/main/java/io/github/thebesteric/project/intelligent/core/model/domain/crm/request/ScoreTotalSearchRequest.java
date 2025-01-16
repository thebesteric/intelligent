package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 积分查询
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:21:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreTotalSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 2743582069811626887L;

    @Schema(description = "客户用户名/姓名/编号")
    private String keyword;

    @Schema(description = "客户等级 ID")
    private Long customerLevelId;

    @Schema(description = "客户类型 ID")
    private Long customerTypeId;
}
