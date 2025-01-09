package io.github.thebesteric.project.intelligent.module.crm.controller.settings;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.Idempotent;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSubAccountCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.CustomerResponse;
import io.github.thebesteric.project.intelligent.core.service.crm.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户-相关设置-账号管理
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 18:22:37
 */
@AgileLogger
@RestController
@RequestMapping("/setting/account")
@RequiredArgsConstructor
@Tag(name = "客户-相关设置-账号管理")
@PreAuthorize("@auth.hasAuthority('customer:management:setting:account')")
public class AccountController {

    private final CustomerService customerService;

    @PostMapping("/page")
    @Operation(summary = "账号列表")
    public R<PagingResponse<CustomerResponse>> page(@Validated @RequestBody CustomerSearchRequest searchRequest) {
        return R.success(customerService.page(searchRequest, null, List.of(AuditStatus.AUDIT_PASS)));
    }

    @GetMapping("/lock")
    @Operation(summary = "账号锁定")
    @Parameter(name = "customerId", description = "客户 ID")
    public R<Void> lock(Long customerId) {
        customerService.lock(customerId);
        return R.success();
    }

    @GetMapping("/unlock")
    @Operation(summary = "账号解锁")
    @Parameter(name = "customerId", description = "客户 ID")
    public R<Void> unlock(Long customerId) {
        customerService.unlock(customerId);
        return R.success();
    }

    @GetMapping("/order/submit/enable")
    @Operation(summary = "提交订单-开启")
    @Parameter(name = "customerId", description = "客户 ID")
    public R<Void> orderSubmitEnable(Long customerId) {
        customerService.orderSubmitEnable(customerId);
        return R.success();
    }

    @GetMapping("/order/submit/disable")
    @Operation(summary = "提交订单-关闭")
    @Parameter(name = "customerId", description = "客户 ID")
    public R<Void> orderSubmitDisable(Long customerId) {
        customerService.orderSubmitDisable(customerId);
        return R.success();
    }

    @Idempotent
    @PostMapping("/sub/account/create")
    @Operation(summary = "添加子账户")
    public R<Void> subAccountCreate(@Validated @RequestBody CustomerSubAccountCreateRequest createRequest) {
        customerService.subAccountCreate(createRequest);
        return R.success();
    }

    @PostMapping("/sub/account/delete")
    @Operation(summary = "删除子账户")
    public R<Void> subAccountDelete() {
        return R.success();
    }

}
