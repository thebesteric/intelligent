package io.github.thebesteric.project.intelligent.module.product.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductCatalogController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 14:00:38
 */
@AgileLogger
@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
@Tag(name = "商品-属性-目录管理")
@PreAuthorize("@auth.hasAuthority('product:attrs:catalog')")
public class ProductCatalogController {

    private final ProductCatalogService catalogService;

    @GetMapping("/tree")
    @Operation(summary = "目录树")
    public R<List<ProductCatalogResponse>> tree() {
        return R.success(catalogService.tree());
    }

}
