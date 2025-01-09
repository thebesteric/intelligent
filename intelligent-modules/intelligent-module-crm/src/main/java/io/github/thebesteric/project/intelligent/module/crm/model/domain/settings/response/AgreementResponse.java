package io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.constant.AgreementType;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.settings.Agreement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * AgreementResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 17:43:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AgreementResponse extends BaseResponse<Agreement> {
    @Serial
    private static final long serialVersionUID = 1794286443509163154L;

    @Schema(description = "协议类型")
    private AgreementType type;

    @Schema(description = "协议内容")
    private String content;
}
