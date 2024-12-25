package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.project.intelligent.modules.common.constant.AuditStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * CustomerAuditRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 18:40:50
 */
@Data
public class CustomerAuditRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1919974914721170366L;

    @Schema(description = "客户 ID")
    @NotNull(message = "客户 ID 不能为空")
    private Long customerId;

    @Schema(description = "审核状态")
    @NotNull(message = "审核状态不能为空")
    private AuditStatus auditStatus;

    @Schema(description = "审核说明")
    private String auditComment;

    @Schema(description = "关联业务员 IDs")
    private List<String> clerkUserIds;

    @Schema(description = "推荐人 ID")
    private Long referrerUserId;
}
