package io.github.thebesteric.project.intelligent.modules.common.service.product;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductBrandCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductBrandUpdateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductBrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductBrand;

import java.util.List;

public interface ProductBrandService extends IBaseService<ProductBrand> {
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
    List<ProductBrandResponse> list(String firstLetter);

    /**
     * 创建品牌
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/20 11:50
     */
    void create(ProductBrandCreateRequest createRequest);

    /**
     * 更新品牌
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/20 11:50
     */
    void update(ProductBrandUpdateRequest updateRequest);

    /**
     * 删除品牌
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/20 14:37
     */
    void delete(Long id);
}
