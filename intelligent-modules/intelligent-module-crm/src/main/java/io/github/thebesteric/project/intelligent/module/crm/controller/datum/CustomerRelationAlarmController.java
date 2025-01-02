package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRelationAlarmResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRelationAlarmService;
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
 * 客户-资料-客勤预警
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-25 15:04:51
 */
@AgileLogger
@RestController
@RequestMapping("/datum/relation-alarm")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客勤预警")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:relation-alarm')")
public class CustomerRelationAlarmController {

    private final CustomerRelationAlarmService relationAlarmService;

    @PostMapping("/page")
    @Operation(summary = "客勤预警列表")
    public R<PagingResponse<CustomerRelationAlarmResponse>> page(@Validated @RequestBody CustomerRelationAlarmSearchRequest searchRequest) {
        return R.success(relationAlarmService.page(searchRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "创建客勤预警")
    public R<Void> create(@Validated @RequestBody CustomerRelationAlarmCreateRequest createRequest) {
        relationAlarmService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新客勤预警")
    public R<Void> update(@Validated @RequestBody CustomerRelationAlarmUpdateRequest updateRequest) {
        relationAlarmService.update(updateRequest);
        return R.success();
    }


}
