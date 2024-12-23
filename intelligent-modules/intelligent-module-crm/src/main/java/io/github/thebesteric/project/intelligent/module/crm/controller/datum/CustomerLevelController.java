package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerDiscountInfoSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.BrandDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CatalogDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerLevelResponse;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerDiscountInfoService;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户-资料-客户等级
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 18:43:32
 */
@AgileLogger
@RestController
@RequestMapping("/datum/level")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户等级")
@PreAuthorize("@auth.hasAuthority('customer:datum:level')")
public class CustomerLevelController {

    private final CustomerLevelService levelService;
    private final CustomerDiscountInfoService discountInfoService;

    @PostMapping("/page")
    @Operation(summary = "等级列表")
    public R<PagingResponse<CustomerLevelResponse>> page(@Validated @RequestBody CustomerLevelSearchRequest searchRequest) {
        return R.success(levelService.page(searchRequest));
    }

    @PostMapping("/create")
    @Operation(summary = "创建等级")
    public R<Void> create(@Validated @RequestBody CustomerLevelCreateRequest createRequest) {
        levelService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改等级")
    public R<Void> update(@Validated @RequestBody CustomerLevelUpdateRequest updateRequest) {
        levelService.update(updateRequest);
        return R.success();
    }

    @PostMapping("/discount/info/settings")
    @Operation(summary = "折扣设置")
    public R<Void> discountInfoSettings(@Validated @RequestBody CustomerDiscountInfoSettingsRequest settingsRequest) {
        discountInfoService.discountInfoSettings(settingsRequest);
        // int i = 1/0;
        return R.success();
    }

    @PostMapping("/discount/info/brand")
    @Operation(summary = "品牌折扣信息")
    @Parameter(name = "customerLevelId", description = "会员等级 ID")
    @Parameter(name = "keyword", description = "名称检索")
    public R<List<BrandDiscountInfoResponse>> discountInfoBrand(@RequestParam Long customerLevelId, @RequestParam(required = false) String keyword) {
        return R.success(discountInfoService.discountInfoBrand(customerLevelId, keyword));
    }

    @PostMapping("/discount/info/catalog")
    @Operation(summary = "目录折扣信息")
    @Parameter(name = "customerLevelId", description = "会员等级 ID")
    public R<List<CatalogDiscountInfoResponse>> discountInfoCatalog(@RequestParam Long customerLevelId) {
        return R.success(discountInfoService.discountInfoCatalog(customerLevelId));
    }

}
