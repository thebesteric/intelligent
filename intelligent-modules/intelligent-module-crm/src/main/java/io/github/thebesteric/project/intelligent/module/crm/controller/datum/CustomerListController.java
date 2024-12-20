package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerService;
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
@PreAuthorize("@auth.hasAuthority('customer:datum:list')")
public class CustomerListController {

    private final CustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "创建客户")
    public R<Void> create(@Validated @RequestBody CustomerCreateRequest createRequest) {
        customerService.create(createRequest);
        return R.success();
    }

}
