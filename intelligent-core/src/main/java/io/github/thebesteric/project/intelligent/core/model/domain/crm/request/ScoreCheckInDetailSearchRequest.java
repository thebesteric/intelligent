package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 签到积分明细列表请求
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-16 15:13:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreCheckInDetailSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = -6575021567109970535L;

    @Schema(description = "客户 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long customerId;

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
