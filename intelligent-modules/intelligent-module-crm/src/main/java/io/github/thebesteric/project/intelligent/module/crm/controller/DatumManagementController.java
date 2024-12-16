package io.github.thebesteric.project.intelligent.module.crm.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资料-客户管理
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-29 09:43:40
 */
@RestController
@RequestMapping("/datum/management")
@RequiredArgsConstructor
@Tag(name = "资料-客户管理")
public class DatumManagementController {

    private final CustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "新增客户")
    public R<Void> create(CustomerCreateRequest createRequest) {
        customerService.create(createRequest);
        return R.success();
    }

}
