package io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.stock.Warehouse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * WarehouseResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 16:26:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WarehouseResponse extends BaseResponse<Warehouse> {
    @Serial
    private static final long serialVersionUID = -4902592611868077890L;

    @Schema(description = "仓库编号")
    private String code;

    @Schema(description = "仓库名称")
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
    private Integer state = 1;

    @Schema(description = "备注")
    private String desc;
}
