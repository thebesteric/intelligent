package io.github.thebesteric.project.intelligent.modules.common.service.product.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.mapper.product.ProductTagMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductTag;
import io.github.thebesteric.project.intelligent.modules.common.service.product.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TagServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 20:43:13
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class ProductTagServiceImpl extends ServiceImpl<ProductTagMapper, ProductTag> implements ProductTagService {
}
