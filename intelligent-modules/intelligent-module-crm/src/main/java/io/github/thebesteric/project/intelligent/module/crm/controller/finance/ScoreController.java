package io.github.thebesteric.project.intelligent.module.crm.controller.finance;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.Idempotent;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.*;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInDetailResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreDetailResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreTotalResponse;
import io.github.thebesteric.project.intelligent.core.service.crm.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ScoreController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:02:24
 */
@AgileLogger
@RestController
@RequestMapping("/finance/score")
@RequiredArgsConstructor
@Tag(name = "客户-财务-积分查询")
@PreAuthorize("@auth.hasAuthority('customer:management:finance:score')")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/page")
    @Operation(summary = "积分列表")
    public R<PagingResponse<ScoreTotalResponse>> page(@Validated @RequestBody ScoreTotalSearchRequest searchRequest) {
        return R.success(scoreService.page(searchRequest));
    }

    @PostMapping("/detail")
    @Operation(summary = "积分明细")
    public R<PagingResponse<ScoreDetailResponse>> detail(@Validated @RequestBody ScoreDetailSearchRequest searchRequest) {
        return R.success(scoreService.detail(searchRequest));
    }

    @Idempotent
    @PostMapping("/adjust")
    @Operation(summary = "积分调整")
    public R<Void> adjust(@Validated @RequestBody ScoreAdjustRequest adjustRequest) {
        scoreService.adjust(adjustRequest);
        return R.success();
    }

    @PostMapping("/checkin/page")
    @Operation(summary = "签到积分列表")
    public R<PagingResponse<ScoreCheckInTotalResponse>> checkInPage(@Validated @RequestBody ScoreCheckInTotalSearchRequest searchRequest) {
        return R.success(scoreService.checkInPage(searchRequest));
    }

    @PostMapping("/checkin/detail")
    @Operation(summary = "签到积分明细")
    public R<PagingResponse<ScoreCheckInDetailResponse>> checkInDetail(@Validated @RequestBody ScoreCheckInDetailSearchRequest searchRequest) {
        return R.success(scoreService.checkInDetail(searchRequest));
    }
}
