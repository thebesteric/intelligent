package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByOperator;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByParam;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerTypePurchaseAuthMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypePurchaseAuthSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.*;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTypePurchaseAuth;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTypePurchaseAuthService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductTagResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.response.WarehouseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductBrand;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductTag;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.stock.Warehouse;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductBrandService;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductCatalogService;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductTagService;
import io.github.thebesteric.project.intelligent.modules.common.service.stock.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerPurchaseAuthServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:49:47
 */
@Service
@RequiredArgsConstructor
public class CustomerTypePurchaseAuthServiceImpl extends ServiceImpl<CustomerTypePurchaseAuthMapper, CustomerTypePurchaseAuth> implements CustomerTypePurchaseAuthService {

    private final ProductBrandService brandService;
    private final ProductCatalogService catalogService;
    private final ProductTagService tagService;
    private final WarehouseService warehouseService;

    /**
     * 采购授权设置
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/24 09:49
     */
    @Override
    public void purchaseAuthSettings(CustomerTypePurchaseAuthSettingsRequest settingsRequest) {
        String tenantId = settingsRequest.getTenantId();
        CustomerTypePurchaseAuth purchaseAuth = this.getByCustomerTypeId(tenantId, settingsRequest.getCustomerTypeId());
        if (purchaseAuth == null) {
            purchaseAuth = settingsRequest.transform();
        }
        purchaseAuth = settingsRequest.merge(purchaseAuth);
        this.saveOrUpdate(purchaseAuth);
    }

    /**
     * 根据客户类型获取采购授权设置
     *
     * @param customerTypeId 客户类型 ID
     *
     * @return CustomerTypePurchaseAuthResponse
     *
     * @author wangweijun
     * @since 2024/12/24 11:23
     */
    @Override
    public CustomerTypePurchaseAuthResponse purchaseAuthGet(Long customerTypeId) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        CustomerTypePurchaseAuth purchaseAuth = this.getByCustomerTypeId(tenantId, customerTypeId);
        CustomerTypePurchaseAuthResponse response = (CustomerTypePurchaseAuthResponse) new CustomerTypePurchaseAuthResponse().transform(purchaseAuth);

        // 处理品牌
        List<String> authBrandIds = purchaseAuth.getBrandIds();
        List<ProductBrand> brands = brandService.listByTenantId(tenantId, OrderByParam.of("keyword", OrderByOperator.ASC));
        List<BrandPurchaseAuthInfoResponse> brandInfoResponses = brands.stream().map(i -> {
            BrandPurchaseAuthInfoResponse info = new BrandPurchaseAuthInfoResponse();
            info.setBrand((ProductBrandResponse) new ProductBrandResponse().transform(i));
            if (authBrandIds.contains(String.valueOf(i.getId()))) {
                info.setPurchaseAuth(true);
            }
            return info;
        }).toList();
        response.setBrandInfos(brandInfoResponses);

        // 处理目录
        List<String> authCategoryIds = purchaseAuth.getCategoryIds();
        List<ProductCatalogResponse> catalogTree = catalogService.tree();
        List<CatalogPurchaseAuthInfoResponse> catalogInfoResponses = catalogTree.stream().map(i -> reversionCatalog(i, authCategoryIds)).toList();
        response.setCategoryInfos(catalogInfoResponses);

        // 处理仓库
        List<String> authWarehouseIds = purchaseAuth.getWarehouseIds();
        List<Warehouse> warehouses = warehouseService.listByTenantId(tenantId, OrderByParam.of("seq", OrderByOperator.ASC));
        List<WarehousePurchaseAuthInfoResponse> warehouseInfoResponses = warehouses.stream().map(i -> {
            WarehousePurchaseAuthInfoResponse info = new WarehousePurchaseAuthInfoResponse();
            info.setWarehouse((WarehouseResponse) new WarehouseResponse().transform(i));
            if (authWarehouseIds.contains(String.valueOf(i.getId()))) {
                info.setPurchaseAuth(true);
            }
            return info;
        }).toList();
        response.setWarehouseInfos(warehouseInfoResponses);

        // 处理标签
        List<String> authTagIds = purchaseAuth.getTagIds();
        List<ProductTag> tags = tagService.listByTenantId(tenantId, OrderByParam.of("seq", OrderByOperator.ASC));
        List<TagPurchaseAuthInfoResponse> tagInfoResponses = tags.stream().map(i -> {
            TagPurchaseAuthInfoResponse info = new TagPurchaseAuthInfoResponse();
            info.setTag((ProductTagResponse) new ProductTagResponse().transform(i));
            if (authTagIds.contains(String.valueOf(i.getId()))) {
                info.setPurchaseAuth(true);
            }
            return info;
        }).toList();
        response.setTagInfos(tagInfoResponses);

        // 处理可购商品
        // 处理禁购商品
        return response;
    }

    public CatalogPurchaseAuthInfoResponse reversionCatalog(ProductCatalogResponse catalog, List<String> authCategoryIds) {
        CatalogPurchaseAuthInfoResponse info = new CatalogPurchaseAuthInfoResponse();
        info.setCatalog(catalog);
        if (authCategoryIds.contains(String.valueOf(catalog.getId()))) {
            info.setPurchaseAuth(true);
        }
        List<ProductCatalogResponse> children = catalog.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            for (ProductCatalogResponse child : children) {
                CatalogPurchaseAuthInfoResponse childResponse = reversionCatalog(child, authCategoryIds);
                info.getChildren().add(childResponse);
            }
        }
        return info;
    }

    /**
     * 根据客户类型获取采购授权设置
     *
     * @param tenantId       租户 ID
     * @param customerTypeId 客户类型 ID
     *
     * @return CustomerTypePurchaseAuth
     *
     * @author wangweijun
     * @since 2024/12/24 11:14
     */
    @Override
    public CustomerTypePurchaseAuth getByCustomerTypeId(String tenantId, Long customerTypeId) {
        return this.lambdaQuery().eq(CustomerTypePurchaseAuth::getTenantId, tenantId)
                .eq(CustomerTypePurchaseAuth::getCustomerTypeId, customerTypeId)
                .eq(CustomerTypePurchaseAuth::getState, 1)
                .one();
    }
}
