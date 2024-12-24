package io.github.thebesteric.project.intelligent.module.stock.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request.WarehouseCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request.WarehouseSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.response.WarehouseResponse;
import io.github.thebesteric.project.intelligent.modules.common.service.stock.WarehouseService;
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
 * WarehouseController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 15:05:49
 */
@AgileLogger
@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
@Tag(name = "库存-仓库-仓库管理")
@PreAuthorize("@auth.hasAuthority('stock:warehouse:management')")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/page")
    @Operation(summary = "仓库列表")
    public R<PagingResponse<WarehouseResponse>> page(@Validated @RequestBody WarehouseSearchRequest searchRequest) {
        return R.success(warehouseService.page(searchRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "添加仓库")
    public R<Void> create(@Validated @RequestBody WarehouseCreateRequest createRequest) {
        warehouseService.create(createRequest);
        return R.success();
    }


}
