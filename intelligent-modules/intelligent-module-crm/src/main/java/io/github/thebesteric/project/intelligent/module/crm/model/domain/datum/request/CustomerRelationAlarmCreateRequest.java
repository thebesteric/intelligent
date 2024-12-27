package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRelationAlarm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.util.List;

/**
 * CustomerRelationAlarmCreateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-25 15:46:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRelationAlarmCreateRequest extends BaseRequest<CustomerRelationAlarm> {
    @Serial
    private static final long serialVersionUID = 3864332496181957935L;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 64, message = "长度范围在 {min} 和 {max} 之间")
    private String name;

    @Schema(description = "拜访频率（多少天拜访 1 次）")
    @NotNull(message = "拜访频率不能为空")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "拜访频率不能小于 {min}")
    private Integer visitIntervalDays;

    @Schema(description = "拜访频率超期提示语")
    private String visitIntervalTips;

    @Schema(description = "下单频率（多少天下单 1 次）")
    @NotNull(message = "下单频率不能为空")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "下单频率不能小于 {min}")
    private Integer orderIntervalDays;

    @Schema(description = "拜访频率超期提示语")
    private String orderIntervalTips;

    @Schema(description = "客户 IDs")
    private List<String> customerIds;

    @Schema(description = "排序")
    private Integer seq = 0;

    public void setSeq(Integer seq) {
        this.seq = seq == null ? 0 : seq;
    }
}
