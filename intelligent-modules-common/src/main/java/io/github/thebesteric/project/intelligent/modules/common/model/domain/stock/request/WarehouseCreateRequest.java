package io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.stock.Warehouse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.util.List;

/**
 * WarehouseCreateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 15:37:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WarehouseCreateRequest extends BaseRequest<Warehouse> {
    @Serial
    private static final long serialVersionUID = -2940698183209655673L;

    @Schema(description = "仓库编号")
    @NotBlank(message = "仓库编号不能为空")
    @Length(min = 1, max = 32, message = "长度范围在 {min} 和 {max} 之间")
    private String code;

    @Schema(description = "仓库名称")
    @NotBlank(message = "仓库名称不能为空")
    @Length(min = 1, max = 64, message = "长度范围在 {min} 和 {max} 之间")
    private String name;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "省市区 ID")
    private Long areaId;

    @Schema(description = "覆盖区域 IDs")
    private List<String> coverRegionIds;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "联系人")
    private String contact;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    @Range(min = 0, max = 1, message = "状态值范围在 {min} 和 {max} 之间")
    private Integer state = 1;

    @Schema(description = "备注")
    private String desc;
}
