package io.github.thebesteric.project.intelligent.module.crm.controller.settings;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.CustomerResponse;
import io.github.thebesteric.project.intelligent.core.service.crm.CustomerService;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AccountManagerController
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

}
