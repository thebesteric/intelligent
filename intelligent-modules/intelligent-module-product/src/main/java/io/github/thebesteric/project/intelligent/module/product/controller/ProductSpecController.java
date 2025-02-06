package io.github.thebesteric.project.intelligent.module.product.controller;

import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品-商品属性-规格管理
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 16:20:59
 */
@AgileLogger
@RestController
@RequestMapping("/spec")
@RequiredArgsConstructor
@Tag(name = "商品-商品属性-规格管理")
@PreAuthorize("@auth.hasAuthority('product:management:attrs:spec')")
public class ProductSpecController {
}
