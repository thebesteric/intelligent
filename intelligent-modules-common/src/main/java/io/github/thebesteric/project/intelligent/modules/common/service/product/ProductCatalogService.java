package io.github.thebesteric.project.intelligent.modules.common.service.product;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductCatalog;

import java.util.List;

public interface ProductCatalogService extends IBaseService<ProductCatalog> {
    /**
     * 根据父级目录 ID 获取目录列表
     *
     * @param tenantId 租户 ID
     * @param parentId 父级目录 ID
     *
     * @return List<ProductCatalog>
     *
     * @author wangweijun
     * @since 2024/12/23 14:15
     */
    List<ProductCatalog> findByParentId(String tenantId, Long parentId);

    /**
     * 获取目录树
     *
     * @return List<CatalogResponse>
     *
     * @author wangweijun
     * @since 2024/12/23 13:45
     */
    List<ProductCatalogResponse> tree();
}
