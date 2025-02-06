package io.github.thebesteric.project.intelligent.module.crm.controller.finance;

import io.github.thebesteric.framework.agile.core.domain.BaseCodeDescEnum;
import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.distributed.locks.annotation.DistributedLock;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidChangeProject;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidType;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidAdjustRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidDetailsRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response.PrepaidDetailsResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response.PrepaidSearchResponse;
import io.github.thebesteric.project.intelligent.modules.common.service.crm.PrepaidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 客户-财务-预存款
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 17:05:05
 */
@AgileLogger
@RestController
@RequestMapping("/finance/prepaid")
@RequiredArgsConstructor
@Tag(name = "客户-财务-预存款")
@PreAuthorize("@auth.hasAuthority('customer:management:finance:prepaid')")
public class PrepaidController {

    private final PrepaidService prepaidService;

    @PostMapping("/page")
    @Operation(summary = "预存款列表")
    public R<PagingResponse<PrepaidSearchResponse>> page(@RequestBody @Validated PrepaidSearchRequest searchRequest) {
        return R.success(prepaidService.page(searchRequest));
    }

    @PostMapping("/details")
    @Operation(summary = "预存款明细")
    public R<PagingResponse<PrepaidDetailsResponse>> details(@RequestBody @Validated PrepaidDetailsRequest detailsRequest) {
        return R.success(prepaidService.details(detailsRequest));
    }

    @DistributedLock(waitTime = 5, leaseTime = 10)
    @PostMapping("/adjust")
    @Operation(summary = "预存款调整")
    public R<Void> adjust(@RequestBody @Validated PrepaidAdjustRequest adjustRequest) {
        prepaidService.adjust(adjustRequest);
        return R.success();
    }

    @GetMapping("/enum/projects")
    @Operation(summary = "预存款变动项目枚举")
    public R<List<Map<String, String>>> prepaidChangeProjects() {
        List<Map<String, String>> list = Arrays.stream(PrepaidChangeProject.values()).map(BaseCodeDescEnum::toMap).toList();
        return R.success(list);
    }

    @GetMapping("/enum/types")
    @Operation(summary = "预存款变动类型枚举")
    public R<List<Map<String, String>>> prepaidTypes() {
        List<Map<String, String>> list = Arrays.stream(PrepaidType.values()).map(BaseCodeDescEnum::toMap).toList();
        return R.success(list);
    }

}
