package io.github.thebesteric.project.intelligent.modules.common.service.stock;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request.WarehouseCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.stock.Warehouse;

public interface WarehouseService extends IBaseService<Warehouse> {
    /**
     * 创建仓库
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/24 15:43
     */
    void create(WarehouseCreateRequest createRequest);
}
