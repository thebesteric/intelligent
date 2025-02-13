package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerCreateRequest;
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
 * 客户-资料-客户列表
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-29 09:43:40
 */
@AgileLogger
@RestController
@RequestMapping("/datum/list")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户列表")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:list')")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/page")
    @Operation(summary = "客户列表")
    public R<PagingResponse<CustomerResponse>> page(@Validated @RequestBody CustomerSearchRequest searchRequest) {
        return R.success(customerService.page(searchRequest, null, List.of(AuditStatus.AUDIT_PASS)));
    }

    @PostMapping("/create")
    @Operation(summary = "创建客户")
    public R<Void> create(@Validated @RequestBody CustomerCreateRequest createRequest) {
        customerService.create(createRequest, AuditStatus.AUDIT_PASS);
        return R.success();
    }

}
