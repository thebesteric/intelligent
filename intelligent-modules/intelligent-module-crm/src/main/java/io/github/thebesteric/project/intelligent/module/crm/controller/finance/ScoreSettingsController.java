package io.github.thebesteric.project.intelligent.module.crm.controller.finance;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.Idempotent;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.ScoreSettingsUpdateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreSettingsResponse;
import io.github.thebesteric.project.intelligent.core.service.crm.ScoreSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户-财务-积分设置
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-17 17:57:06
 */
@AgileLogger
@RestController
@RequestMapping("/finance/settings")
@RequiredArgsConstructor
@Tag(name = "客户-财务-积分设置")
@PreAuthorize("@auth.hasAuthority('customer:management:finance:settings')")
public class ScoreSettingsController {

    private final ScoreSettingsService scoreSettingsService;

    @GetMapping("/details")
    @Operation(summary = "积分设置-详情")
    public R<ScoreSettingsResponse> details() {
        return R.success(scoreSettingsService.details());
    }

    @Idempotent
    @PostMapping("/update")
    @Operation(summary = "积分设置-更新")
    public R<Void> update(@RequestBody @Validated ScoreSettingsUpdateRequest updateRequest) {
        scoreSettingsService.update(updateRequest);
        return R.success();
    }

}
