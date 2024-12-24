package io.github.thebesteric.project.intelligent.modules.common.mapper.stock;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.stock.Warehouse;

/**
 * WarehouseMapper
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 14:27:51
 */
@DS(ApplicationConstants.Application.Module.Stock.DATASOURCE_INTELLIGENT_MODULE_STOCK)
public interface WarehouseMapper extends IBaseMapper<Warehouse> {
}
