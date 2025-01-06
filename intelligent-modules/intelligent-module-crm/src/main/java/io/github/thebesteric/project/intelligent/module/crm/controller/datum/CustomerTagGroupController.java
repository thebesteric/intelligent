package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagGroupResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTagGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户-资料-客户标签
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 11:44:35
 */
@AgileLogger
@RestController
@RequestMapping("/datum/tag/group")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户标签组")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:tag')")
public class CustomerTagGroupController {

    private final CustomerTagGroupService tagGroupService;

    @PostMapping("/page")
    @Operation(summary = "客户标签组列表")
    public R<PagingResponse<CustomerTagGroupResponse>> page(@Validated @RequestBody CustomerTagGroupSearchRequest searchRequest) {
        return R.success(tagGroupService.page(searchRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "创建客户标签组")
    public R<Void> create(@Validated @RequestBody CustomerTagGroupCreateRequest createRequest) {
        tagGroupService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新客户标签组")
    public R<Void> update(@Validated @RequestBody CustomerTagGroupUpdateRequest updateRequest) {
        tagGroupService.update(updateRequest);
        return R.success();
    }

    @GetMapping("/delete")
    @Operation(summary = "删除客户标签组")
    @Parameter(name = "id", description = "ID")
    public R<Void> delete(@RequestParam Long id) {
        tagGroupService.delete(id);
        return R.success();
    }

}
