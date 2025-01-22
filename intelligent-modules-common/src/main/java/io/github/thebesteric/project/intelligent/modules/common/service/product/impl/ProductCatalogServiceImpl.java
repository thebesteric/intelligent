package io.github.thebesteric.project.intelligent.modules.common.service.product.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.modules.common.mapper.product.ProductCatalogMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductCatalog;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CatalogServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 20:42:58
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.Product.DATASOURCE_INTELLIGENT_MODULE_PRODUCT)
public class ProductCatalogServiceImpl extends ServiceImpl<ProductCatalogMapper, ProductCatalog> implements ProductCatalogService {
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
    @Override
    public List<ProductCatalog> findByParentId(String tenantId, Long parentId) {
        LambdaQueryChainWrapper<ProductCatalog> query = this.lambdaQuery().eq(ProductCatalog::getTenantId, tenantId);
        if (parentId == null) {
            query.isNull(ProductCatalog::getParentId);
        } else {
            query.eq(ProductCatalog::getParentId, parentId);
        }
        return query.orderByAsc(ProductCatalog::getSeq).list();
    }

    /**
     * 获取目录树
     *
     * @return List<CatalogResponse>
     *
     * @author wangweijun
     * @since 2024/12/23 13:45
     */
    @Override
    public List<ProductCatalogResponse> tree() {
        String tenantId = SecurityUtils.getTenantIdWithException();
        List<ProductCatalog> parents = findByParentId(tenantId, null);
        return recursion(tenantId, parents);
    }

    /**
     * 递归目录树
     *
     * @param tenantId 租户 ID
     * @param parents  父目录
     *
     * @return List<CatalogResponse>
     *
     * @author wangweijun
     * @since 2024/12/23 13:46
     */
    private List<ProductCatalogResponse> recursion(String tenantId, List<ProductCatalog> parents) {
        List<ProductCatalogResponse> catalogResponses = new ArrayList<>();
        parents.forEach(parent -> {
            List<ProductCatalog> children = findByParentId(tenantId, parent.getId());
            List<ProductCatalogResponse> childResponses = recursion(tenantId, children);
            ProductCatalogResponse parentResponse = new ProductCatalogResponse().transform(parent);
            parentResponse.setChildren(childResponses);
            catalogResponses.add(parentResponse);
        });
        return catalogResponses;
    }

}
