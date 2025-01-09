package io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.constant.AgreementType;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.settings.Agreement;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * AgreementSettingsRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-08 12:40:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AgreementSettingsRequest extends BaseRequest<Agreement> {
    @Serial
    private static final long serialVersionUID = -8467496678144519351L;

    @Schema(description = "协议类型")
    @NotNull(message = "协议类型不能为空")
    private AgreementType type;

    @Schema(description = "协议内容")
    private String content;
}
