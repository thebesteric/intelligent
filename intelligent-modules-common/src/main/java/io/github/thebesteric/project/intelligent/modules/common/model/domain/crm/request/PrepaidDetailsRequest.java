package io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidChangeProject;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * PrepaidDetailsRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-02-06 10:05:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PrepaidDetailsRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = -2308627301723113034L;

    @Schema(description = "客户 ID")
    @NotNull(message = "客户 ID 不能为空")
    private Long customerId;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    @Schema(description = "变动类型")
    private PrepaidType type;

    @Schema(description = "变动项目")
    private PrepaidChangeProject changeProject;

    public Date getStartDate() {
        // 将 startDate 转换为当天的开始时间（00:00:00）
        if (startDate != null) {
            LocalDateTime startOfDay = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
            startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
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

    public void setType(String type) {
        if (StringUtils.isNotBlank(type)) {
            this.type = PrepaidType.of(type);
        }
    }

    public void setChangeProject(String changeProject) {
        if (StringUtils.isNotBlank(changeProject)) {
            this.changeProject = PrepaidChangeProject.of(changeProject);
        }
    }
}
