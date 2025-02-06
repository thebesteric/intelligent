package io.github.thebesteric.project.intelligent.module.product.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.Idempotent;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 15:55:54
 */
@AgileLogger
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "商品-商品管理-商品管理")
public class ProductController {

    @Idempotent
    @PreAuthorize("@auth.hasAuthority('product:management:control:add')")
    @PostMapping("/add")
    @Operation(summary = "添加商品")
    public R<Void> add() {
        return R.success();
    }

    @PreAuthorize("@auth.hasAuthority('product:management:control:list')")
    @PostMapping("/page")
    @Operation(summary = "商品列表")
    public R<Void> page() {
        return R.success();
    }

}
