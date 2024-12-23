package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.MapWrapper;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByOperator;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByParam;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.module.crm.constant.DiscountType;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerDiscountInfoMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerDiscountInfoSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.BrandDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CatalogDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerDiscountInfo;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerLevel;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerDiscountInfoService;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerLevelService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductCatalogResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductBrand;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductBrandService;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CustomerDiscountInfoServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:47:21
 */
@Service
@RequiredArgsConstructor
public class CustomerDiscountInfoServiceImpl extends ServiceImpl<CustomerDiscountInfoMapper, CustomerDiscountInfo> implements CustomerDiscountInfoService {

    private final CustomerLevelService customerLevelService;
    private final ProductBrandService brandService;
    private final ProductCatalogService catalogService;

    /**
     * 折扣设置
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/19 16:31
     */
    @Transactional
    @Override
    public void discountInfoSettings(CustomerDiscountInfoSettingsRequest settingsRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Long customerLevelId = settingsRequest.getCustomerLevelId();
        // 删除该等级下所属类型的所有折扣信息
        CustomerDiscountInfoMapper discountInfoMapper = this.getBaseMapper();
        Map<String, Object> params = MapWrapper.createLambda(CustomerDiscountInfo.class, MapWrapper.KeyStyle.SNAKE_CASE)
                .put(CustomerDiscountInfo::getTenantId, tenantId)
                .put(CustomerDiscountInfo::getCustomerLevelId, customerLevelId)
                .put(CustomerDiscountInfo::getDiscountType, settingsRequest.getDiscountType())
                .build();
        discountInfoMapper.physicalDeleteByColumns(CustomerDiscountInfo.class, params);
        // 添加新的折扣信息
        Optional.ofNullable(settingsRequest.getDiscountInfos())
                .filter(l -> !l.isEmpty())
                .ifPresent(l -> l.forEach(discountInfo -> {
                    DiscountType discountType = settingsRequest.getDiscountType();
                    Long discountObjectId = discountInfo.getDiscountObjectId();
                    Double discountRate = discountInfo.getDiscountRate();
                    CustomerDiscountInfo customerDiscountInfo = CustomerDiscountInfo.of(tenantId, customerLevelId, discountType, discountObjectId, discountRate);
                    this.save(customerDiscountInfo);
                }));
        // 设置当前生效的折扣类型
        CustomerLevel customerLevel = customerLevelService.getById(customerLevelId);
        customerLevel.setDiscountType(settingsRequest.getDiscountType());
        customerLevelService.updateById(customerLevel);
    }

    /**
     * 品牌折扣信息
     *
     * @param customerLevelId 会员等级 ID
     * @param keyword         名称检索
     *
     * @return List<BrandDiscountInfoResponse>
     *
     * @author wangweijun
     * @since 2024/12/20 16:58
     */
    @Override
    public List<BrandDiscountInfoResponse> discountInfoBrand(Long customerLevelId, String keyword) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        List<ProductBrand> brands = brandService.findByTenantId(tenantId, OrderByParam.of("keyword", OrderByOperator.ASC));
        List<BrandDiscountInfoResponse> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(brands)) {
            responses = brands.stream().map(brand -> {
                BrandDiscountInfoResponse response = new BrandDiscountInfoResponse();
                // 获取唯一折扣信息
                CustomerDiscountInfo discountInfo = this.getUnique(tenantId, customerLevelId, DiscountType.BRAND, brand.getId());
                if (discountInfo != null) {
                    CustomerDiscountInfoResponse discountInfoResponse = (CustomerDiscountInfoResponse) new CustomerDiscountInfoResponse().transform(discountInfo);
                    response.setDiscountInfoResponse(discountInfoResponse);
                }
                // 获取品牌信息
                ProductBrandResponse brandResponse = (ProductBrandResponse) new ProductBrandResponse().transform(brand);
                response.setBrandResponse(brandResponse);
                return response;
            }).toList();
        }
        return responses;
    }

    /**
     * 目录折扣信息
     *
     * @param customerLevelId 会员等级 ID
     *
     * @return List<BrandDiscountInfoResponse>
     *
     * @author wangweijun
     * @since 2024/12/23 15:25
     */
    @Override
    public List<CatalogDiscountInfoResponse> discountInfoCatalog(Long customerLevelId) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        List<ProductCatalogResponse> catalogs = catalogService.tree();
        List<CatalogDiscountInfoResponse> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(catalogs)) {
            responses = reversion(tenantId, customerLevelId, catalogs);
        }
        return responses;
    }

    /**
     * 递归目录折扣信息
     *
     * @param tenantId        租户 ID
     * @param customerLevelId 等级 ID
     * @param parents         父级目录
     *
     * @return List<CatalogDiscountInfoResponse>
     *
     * @author wangweijun
     * @since 2024/12/23 16:08
     */
    private List<CatalogDiscountInfoResponse> reversion(String tenantId, Long customerLevelId, List<ProductCatalogResponse> parents) {
        return parents.stream().map(parent -> {
            CatalogDiscountInfoResponse response = new CatalogDiscountInfoResponse();
            response.setCatalogResponse(parent);
            // 获取唯一折扣信息
            CustomerDiscountInfo discountInfo = this.getUnique(tenantId, customerLevelId, DiscountType.CATALOG, parent.getId());
            if (discountInfo != null) {
                CustomerDiscountInfoResponse discountInfoResponse = (CustomerDiscountInfoResponse) new CustomerDiscountInfoResponse().transform(discountInfo);
                response.setDiscountInfoResponse(discountInfoResponse);
            }
            List<ProductCatalogResponse> children = parent.getChildren();
            if (CollectionUtils.isNotEmpty(children)) {
                List<CatalogDiscountInfoResponse> childrenResponses = reversion(tenantId, customerLevelId, children);
                response.setChildren(childrenResponses);
            }
            return response;
        }).toList();
    }

    /**
     * 获取客户折扣信息
     *
     * @param tenantId         租户 ID
     * @param customerLevelId  等级 ID
     * @param discountType     折扣类型
     * @param discountObjectId 折扣对象 ID
     *
     * @return CustomerDiscountInfo
     *
     * @author wangweijun
     * @since 2024/12/20 18:00
     */
    public CustomerDiscountInfo getUnique(String tenantId, Long customerLevelId, DiscountType discountType, Long discountObjectId) {
        Map<String, Object> queryParams = MapWrapper.createLambda(CustomerDiscountInfo.class, MapWrapper.KeyStyle.SNAKE_CASE)
                .put(CustomerDiscountInfo::getTenantId, tenantId)
                .put(CustomerDiscountInfo::getCustomerLevelId, customerLevelId)
                .put(CustomerDiscountInfo::getDiscountType, discountType)
                .put(CustomerDiscountInfo::getDiscountObjectId, discountObjectId).build();
        return this.getByParams(queryParams);
    }
}
