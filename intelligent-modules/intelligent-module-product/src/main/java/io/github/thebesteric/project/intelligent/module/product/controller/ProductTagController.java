package io.github.thebesteric.project.intelligent.module.product.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductTagSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductTagResponse;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductTagService;
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
 * ProductTagController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 18:01:44
 */
@AgileLogger
@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
@Tag(name = "商品-属性-标签管理")
@PreAuthorize("@auth.hasAuthority('product:management:attrs:tag')")
public class ProductTagController {

    private final ProductTagService tagService;

    @PostMapping("/page")
    @Operation(summary = "标签列表")
    public R<PagingResponse<ProductTagResponse>> page(@Validated @RequestBody ProductTagSearchRequest searchRequest) {
        return R.success(tagService.page(searchRequest));
    }

}
