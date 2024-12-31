package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTagService;
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
@RequestMapping("/datum/tag")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户标签")
@PreAuthorize("@auth.hasAuthority('customer:management:datum:tag')")
public class CustomerTagController {

    private final CustomerTagService tagService;

    @PostMapping("/page")
    @Operation(summary = "客户标签列表")
    public R<PagingResponse<CustomerTagResponse>> page(@Validated @RequestBody CustomerTagSearchRequest searchRequest) {
        return R.success(tagService.page(searchRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "创建客户标签")
    public R<Void> create(@Validated @RequestBody CustomerTagCreateRequest createRequest) {
        tagService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新客户标签")
    public R<Void> update(@Validated @RequestBody CustomerTagUpdateRequest updateRequest) {
        tagService.update(updateRequest);
        return R.success();
    }

    @GetMapping("/delete")
    @Operation(summary = "删除客户标签")
    @Parameter(name = "id", description = "ID")
    public R<Void> delete(@RequestParam Long id) {
        tagService.delete(id);
        return R.success();
    }

}
