package io.github.thebesteric.project.intelligent.module.crm.controller.settings;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.Idempotent;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.constant.AgreementType;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.request.AgreementSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.response.AgreementResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.settings.AgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * AgreementController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-08 11:38:55
 */
@AgileLogger
@RestController
@RequestMapping("/setting/agreement")
@RequiredArgsConstructor
@Tag(name = "客户-相关设置-协议管理")
@PreAuthorize("@auth.hasAuthority('customer:management:setting:account')")
public class AgreementController {

    private final AgreementService agreementService;

    @Idempotent
    @PostMapping("/settings")
    @Operation(summary = "协议内容设置")
    public R<Void> settings(@Validated @RequestBody AgreementSettingsRequest settingsRequest) {
        agreementService.settings(settingsRequest);
        return R.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "获取协议内容")
    @Parameter(name = "type", description = "协议类型", required = true)
    public R<AgreementResponse> detail(AgreementType type) {
        return R.success(agreementService.detail(type));
    }

}
