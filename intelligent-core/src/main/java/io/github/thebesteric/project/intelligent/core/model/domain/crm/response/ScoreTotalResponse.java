package io.github.thebesteric.project.intelligent.core.model.domain.crm.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分总计（明细合并）响应
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 18:41:37
 */
@Data
public class ScoreTotalResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3433731493343374832L;

    @Schema(description = "客户 ID")
    private Long customerId;

    @Schema(description = "客户账号")
    private String customerUsername;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "客户编号")
    private String customerSerialNo;

    @Schema(description = "客户等级 ID")
    private Long customerLevelId;

    @Schema(description = "客户等级名称")
    private String customerLevelName;

    @Schema(description = "客户类型 ID")
    private Long customerTypeId;

    @Schema(description = "客户类型名称")
    private String customerTypeName;

    @Schema(description = "积分总计")
    private BigDecimal totalScore;

    @Schema(description = "已使用积分")
    private BigDecimal totalUsedScore;

    @Schema(description = "最近使用积分时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUsedAt;

    @Schema(description = "最近调整积分时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastAdjustAt;

}
