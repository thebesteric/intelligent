package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.github.thebesteric.project.intelligent.core.constant.crm.ConsumeType;
import io.github.thebesteric.project.intelligent.core.constant.crm.ScoreType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 积分查询
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:21:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreDetailSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 2743582069811626887L;

    @Schema(description = "客户 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long customerId;

    @Schema(description = "积分类型", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ScoreType scoreType;

    @Schema(description = "消费类型", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ConsumeType consumeType;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    public Date getStartDate() {
        // 将 startDate 转换为当天的开始时间（00:00:00）
        if (startDate != null) {
            LocalDateTime startOfDay = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
            startDate= Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        }
        return startDate;
    }

    public Date getEndDate() {
        // 将 endDate 转换为当天的结束时间（23:59:59）
        if (endDate != null) {
            LocalDateTime endOfDay = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(LocalTime.MAX);
            endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        }
        return endDate;
    }
}
