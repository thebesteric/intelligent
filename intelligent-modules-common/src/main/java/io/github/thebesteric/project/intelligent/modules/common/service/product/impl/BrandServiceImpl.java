package io.github.thebesteric.project.intelligent.modules.common.service.product.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.modules.common.mapper.product.BrandMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.BrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.Brand;
import io.github.thebesteric.project.intelligent.modules.common.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
            brands = listByParams(Map.of("tenant_id", tenantId));
        }
        return brands.stream().map(brand -> (BrandResponse) new BrandResponse().transform(brand)).toList();
    }
}
