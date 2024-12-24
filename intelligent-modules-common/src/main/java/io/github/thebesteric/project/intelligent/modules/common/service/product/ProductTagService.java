package io.github.thebesteric.project.intelligent.modules.common.service.product;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductTagSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductTagResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductTag;

/**
 * TagService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 20:38:17
 */
public interface ProductTagService extends IBaseService<ProductTag> {

    /**
     * 分页列表
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<ProductTagResponse>
     *
     * @author wangweijun
     * @since 2024/12/24 18:07
     */
    PagingResponse<ProductTagResponse> page(ProductTagSearchRequest searchRequest);
}
