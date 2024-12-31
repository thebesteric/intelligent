package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUserSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionUserResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRegionUserService;
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
 * CustomerRegionController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 10:30:17
 */
@AgileLogger
@RestController
@RequestMapping("/datum/region/user")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户区域-关联用户")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:region')")
public class CustomerRegionUserController {

    private final CustomerRegionUserService regionUserService;

    @PostMapping("/page")
    @Operation(summary = "客户区域-关联用户列表")
    public R<PagingResponse<CustomerRegionUserResponse>> page(@Validated @RequestBody CustomerRegionUserSearchRequest searchRequest) {
        return R.success(regionUserService.page(searchRequest));
    }
}
