package io.github.thebesteric.project.intelligent.modules.common.service.product.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByOperator;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByParam;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.modules.common.mapper.product.ProductBrandMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductBrandCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductBrandUpdateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductBrand;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductBrandService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BrandServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 20:30:55
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.Product.DATASOURCE_INTELLIGENT_MODULE_PRODUCT)
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements ProductBrandService {
    /**
     * 品牌列表
     *
     * @param firstLetter 首字母
     *
     * @return List<BrandResponse>
     *
     * @author wangweijun
     * @since 2024/12/19 20:51
     */
    @Override
    public List<ProductBrandResponse> list(String firstLetter) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        List<ProductBrand> brands;
        if (StringUtils.isNotBlank(firstLetter)) {
            brands = this.baseMapper.findByFirstLetter(tenantId, firstLetter);
        } else {
            brands = this.listByTenantId(tenantId, OrderByParam.of("keyword", OrderByOperator.ASC));
        }
        return brands.stream().map(brand -> (ProductBrandResponse) new ProductBrandResponse().transform(brand)).toList();
    }

    /**
     * 创建品牌
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/20 11:50
     */
    @Override
    public void create(ProductBrandCreateRequest createRequest) {
        // 转换
        ProductBrand brand = createRequest.transform();
        // 保存
        this.save(brand);
    }

    /**
     * 更新品牌
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/20 11:50
     */
    @Override
    public void update(ProductBrandUpdateRequest updateRequest) {
        ProductBrand brand = getByTenantAndId(updateRequest.getTenantId(), updateRequest.getId());
        // 合并
        brand = updateRequest.merge(brand);
        // 更新
        this.updateById(brand);
    }

    /**
     * 删除品牌
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/20 14:37
     */
    @Override
    public void delete(Long id) {
        String tenantId = SecurityUtils.getTenantId();
        this.deleteByTenantAndId(tenantId, id);
    }
}
