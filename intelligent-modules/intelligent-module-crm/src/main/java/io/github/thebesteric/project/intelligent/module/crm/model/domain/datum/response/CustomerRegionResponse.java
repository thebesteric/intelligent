package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRegion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户区域
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 10:44:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegionResponse extends BaseResponse<CustomerRegion> {
    @Serial
    private static final long serialVersionUID = 6171477821659671496L;

    @Schema(description = "区域名称")
    private String name;

    @Schema(description = "区域编码")
    private String code;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "父级 ID")
    private Long parentId;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    private Integer state = 1;
}
