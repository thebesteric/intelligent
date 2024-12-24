package io.github.thebesteric.project.intelligent.modules.common.mapper.product;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductCatalog;

@DS(ApplicationConstants.Application.Module.Product.DATASOURCE_INTELLIGENT_MODULE_PRODUCT)
public interface ProductCatalogMapper extends IBaseMapper<ProductCatalog> {
}
