package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import cn.hutool.core.lang.tree.Tree;
import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户-资料-客户区域
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 10:30:17
 */
@AgileLogger
@RestController
@RequestMapping("/datum/region")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户区域")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:region')")
public class CustomerRegionController {

    private final CustomerRegionService regionService;

    @PostMapping("/tree")
    @Operation(summary = "客户区域列表")
    public R<List<Tree<Long>>> tree() {
        return R.success(regionService.tree());
    }

    @GetMapping("/child/regions")
    @Operation(summary = "获取子级区列表（包含自身）")
    @Parameter(name = "regionId", description = "区域 ID")
    public R<List<CustomerRegionResponse>> childRegions(@RequestParam(required = false) Long regionId) {
        return R.success(regionService.childRegions(regionId));
    }

    @PostMapping("/create")
    @Operation(summary = "创建客户区域")
    public R<Void> create(@Validated @RequestBody CustomerRegionCreateRequest createRequest) {
        regionService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新客户区域")
    public R<Void> update(@Validated @RequestBody CustomerRegionUpdateRequest updateRequest) {
        regionService.update(updateRequest);
        return R.success();
    }

    @GetMapping("/delete")
    @Operation(summary = "删除客户区域")
    @Parameter(name = "regionId", description = "区域 ID")
    public R<Void> delete(@RequestParam Long regionId) {
        regionService.delete(regionId);
        return R.success();
    }
}
