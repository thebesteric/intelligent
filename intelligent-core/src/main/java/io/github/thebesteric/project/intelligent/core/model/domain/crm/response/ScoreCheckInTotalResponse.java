package io.github.thebesteric.project.intelligent.core.model.domain.crm.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 签到积分（明细合并）列表
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-16 15:12:13
 */
@Data
public class ScoreCheckInTotalResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -5622272416627958875L;

    @Schema(description = "客户 ID")
    private Long customerId;

    @Schema(description = "客户账号")
    private String customerUsername;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "客户编号")
    private String customerSerialNo;

    @Schema(description = "签到总积分")
    private BigDecimal totalCheckInScore;

    @Schema(description = "最后签到积分")
    private BigDecimal lastCheckInScore;

    @Schema(description = "最后签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastCheckInAt;
}
