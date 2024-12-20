package io.github.thebesteric.project.intelligent.modules.common.service.product.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByOperator;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByParam;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.modules.common.mapper.product.BrandMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.BrandCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.BrandUpdateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.BrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.Brand;
import io.github.thebesteric.project.intelligent.modules.common.service.product.BrandService;
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
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
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
    public List<BrandResponse> list(String firstLetter) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        List<Brand> brands;
        if (StringUtils.isNotBlank(firstLetter)) {
            brands = this.baseMapper.findByFirstLetter(tenantId, firstLetter);
        } else {
            brands = findByTenantId(tenantId, OrderByParam.of("keyword", OrderByOperator.ASC));
        }
        return brands.stream().map(brand -> (BrandResponse) new BrandResponse().transform(brand)).toList();
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
    public void create(BrandCreateRequest createRequest) {
        // 转换
        Brand brand = createRequest.transform();
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
    public void update(BrandUpdateRequest updateRequest) {
        Brand brand = getByTenantAndId(updateRequest.getTenantId(), updateRequest.getId());
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
