package io.github.thebesteric.project.intelligent.module.product.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.BrandCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.BrandUpdateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.BrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.service.product.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品-属性-品牌管理
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 19:27:31
 */
@AgileLogger
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Tag(name = "商品-属性-品牌管理")
@PreAuthorize("@auth.hasAuthority('product:attrs:brand')")
public class ProductBrandController {

    private final BrandService brandService;

    @GetMapping("/list")
    @Operation(summary = "品牌列表")
    @Parameter(name = "firstLetter", description = "首字母")
    public R<List<BrandResponse>> list(@RequestParam(required = false) String firstLetter) {
        return R.success(brandService.list(firstLetter));
    }

    @PostMapping("/create")
    @Operation(summary = "创建品牌")
    public R<Void> create(@Validated @RequestBody BrandCreateRequest createRequest) {
        brandService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新品牌")
    public R<Void> update(@Validated @RequestBody BrandUpdateRequest updateRequest) {
        brandService.update(updateRequest);
        return R.success();
    }

    @GetMapping("/delete")
    @Operation(summary = "删除品牌")
    @Parameter(name = "id", description = "ID")
    public R<Void> delete(@RequestParam Long id) {
        brandService.delete(id);
        return R.success();
    }

}
