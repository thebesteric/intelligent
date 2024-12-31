package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRegion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;

/**
 * 客户区域-新增
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 11:33:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegionCreateRequest extends BaseRequest<CustomerRegion> {
    @Serial
    private static final long serialVersionUID = -2354984472873890086L;

    @Schema(description = "区域名称")
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 32, message = "名称长度范围在 {min} 和 {max} 之间")
    private String name;

    @Schema(description = "区域编码")
    private String code;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "父级 ID")
    private Long parentId;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    @Range(min = 0, max = 1, message = "状态值范围在 {min} 和 {max} 之间")
    private Integer state = 1;
}
