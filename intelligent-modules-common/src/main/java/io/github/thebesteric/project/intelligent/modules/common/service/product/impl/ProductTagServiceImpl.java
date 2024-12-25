package io.github.thebesteric.project.intelligent.modules.common.service.product.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.mapper.product.ProductTagMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request.ProductTagSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.product.response.ProductTagResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductTag;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TagServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 20:43:13
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.Product.DATASOURCE_INTELLIGENT_MODULE_PRODUCT)
public class ProductTagServiceImpl extends ServiceImpl<ProductTagMapper, ProductTag> implements ProductTagService {

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
    @Override
    public PagingResponse<ProductTagResponse> page(ProductTagSearchRequest searchRequest) {
        IPage<ProductTag> page = this.lambdaQuery()
                .like(StringUtils.isNotBlank(searchRequest.getName()), ProductTag::getName, searchRequest.getName())
                .page(searchRequest.getPage(ProductTag.class));
        List<ProductTagResponse> records = page.getRecords().stream().map(r -> (ProductTagResponse) new ProductTagResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }
}
