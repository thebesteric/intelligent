package io.github.thebesteric.project.intelligent.modules.common.service.product;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
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

}
