package io.github.thebesteric.project.intelligent.modules.common.service.product;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.BrandCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.BrandUpdateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.BrandResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.Brand;

import java.util.List;

public interface BrandService extends IBaseService<Brand> {
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
    List<BrandResponse> list(String firstLetter);

    /**
     * 创建品牌
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/20 11:50
     */
    void create(BrandCreateRequest createRequest);

    /**
     * 更新品牌
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/20 11:50
     */
    void update(BrandUpdateRequest updateRequest);

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
