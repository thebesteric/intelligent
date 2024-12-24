package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypePurchaseAuthSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTypePurchaseAuthResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTypeResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTypePurchaseAuthService;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户-资料-客户类型
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 17:58:23
 */
@AgileLogger
@RestController
@RequestMapping("/datum/type")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户类型")
@PreAuthorize("@auth.hasAuthority('customer:datum:type')")
public class CustomerTypeController {

    private final CustomerTypeService customerTypeService;
    private final CustomerTypePurchaseAuthService purchaseAuthService;

    @PostMapping("/page")
    @Operation(summary = "客户类型列表")
    public R<PagingResponse<CustomerTypeResponse>> page(@Validated @RequestBody CustomerTypeSearchRequest searchRequest) {
        return R.success(customerTypeService.page(searchRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "创建客户类型")
    public R<Void> create(@Validated @RequestBody CustomerTypeCreateRequest createRequest) {
        customerTypeService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改客户类型")
    public R<Void> update(@Validated @RequestBody CustomerTypeUpdateRequest updateRequest) {
        customerTypeService.update(updateRequest);
        return R.success();
    }

    @GetMapping("/delete")
    @Operation(summary = "删除客户类型")
    @Parameter(name = "id", description = "ID")
    public R<Void> delete(@RequestParam Long id) {
        customerTypeService.delete(id);
        return R.success();
    }

    @GetMapping("/purchase/auth/get")
    @Operation(summary = "获取采购授权")
    @Parameter(name = "customerTypeId", description = "客户类型 ID")
    public R<CustomerTypePurchaseAuthResponse> purchaseAuthGet(@RequestParam Long customerTypeId) {
        return R.success(purchaseAuthService.purchaseAuthGet(customerTypeId));
    }

    @PostMapping("/purchase/auth/settings")
    @Operation(summary = "采购授权设置")
    public R<Void> purchaseAuthSettings(@Validated @RequestBody CustomerTypePurchaseAuthSettingsRequest settingsRequest) {
        purchaseAuthService.purchaseAuthSettings(settingsRequest);
        return R.success();
    }
}
