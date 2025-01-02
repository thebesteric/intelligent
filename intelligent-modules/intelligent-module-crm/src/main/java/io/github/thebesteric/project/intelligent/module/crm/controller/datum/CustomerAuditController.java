package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.constant.RegisterSource;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerAuditRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户-资料-客户审核
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 17:31:08
 */
@AgileLogger
@RestController
@RequestMapping("/datum/audit")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户审核")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:audit')")
public class CustomerAuditController {

    private final CustomerService customerService;

    @PostMapping("/page")
    @Operation(summary = "审核列表")
    public R<PagingResponse<CustomerResponse>> page(@Validated @RequestBody CustomerSearchRequest searchRequest) {
        return R.success(customerService.page(searchRequest, RegisterSource.OUTER, null));
    }

    @GetMapping("/detail")
    @Operation(summary = "审核详情")
    public R<CustomerResponse> detail(@RequestParam Long customerId) {
        return R.success(customerService.detail(customerId));
    }

    @PostMapping
    @Operation(summary = "客户审核")
    public R<Void> audit(@Validated @RequestBody CustomerAuditRequest auditRequest) {
        customerService.audit(auditRequest);
        return R.success();
    }

}
