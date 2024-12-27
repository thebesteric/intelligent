package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRelationAlarm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 客勤预警响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-26 10:51:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRelationAlarmResponse extends BaseResponse<CustomerRelationAlarm> {
    @Serial
    private static final long serialVersionUID = -5942660657703016384L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "拜访频率（多少天拜访 1 次）")
    private Integer visitIntervalDays;

    @Schema(description = "拜访频率超期提示语")
    private String visitIntervalTips;

    @Schema(description = "下单频率（多少天下单 1 次）")
    private Integer orderIntervalDays;

    @Schema(description = "拜访频率超期提示语")
    private String orderIntervalTips;

    @Schema(description = "客户 IDs")
    private List<String> customerIds;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer state = 1;
}
