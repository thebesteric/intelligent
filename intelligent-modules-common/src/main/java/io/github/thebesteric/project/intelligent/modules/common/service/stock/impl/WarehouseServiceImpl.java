package io.github.thebesteric.project.intelligent.modules.common.service.stock.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.modules.common.mapper.stock.WarehouseMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request.WarehouseCreateRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request.WarehouseSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.response.WarehouseResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.stock.Warehouse;
import io.github.thebesteric.project.intelligent.modules.common.service.stock.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WarehouseServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 14:48:44
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.Stock.DATASOURCE_INTELLIGENT_MODULE_STOCK)
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {

    /**
     * 分页列表
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<WarehouseResponse>
     *
     * @author wangweijun
     * @since 2024/12/24 17:52
     */
    @Override
    public PagingResponse<WarehouseResponse> page(WarehouseSearchRequest searchRequest) {
        IPage<Warehouse> page = this.lambdaQuery()
                .and(StringUtils.isNotBlank(searchRequest.getKeyword()), query ->
                        query.like(Warehouse::getName, searchRequest.getKeyword())
                                .or()
                                .like(Warehouse::getCode, searchRequest.getKeyword()))
                .page(searchRequest.getPage(Warehouse.class));
        List<WarehouseResponse> records = page.getRecords().stream().map(r -> (WarehouseResponse) new WarehouseResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 创建仓库
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/24 15:43
     */
    @Override
    public void create(WarehouseCreateRequest createRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        DataValidator dataValidator = DataValidator.create(BizException.class);
        // 检查名称或编码是否重复
        Warehouse warehouse = this.lambdaQuery().eq(Warehouse::getTenantId, tenantId)
                .and(query -> query.eq(Warehouse::getName, createRequest.getName()).or().eq(Warehouse::getCode, createRequest.getCode()))
                .one();
        dataValidator.validate(warehouse != null, new DataAlreadyExistsException("仓库名称/编码重复"));
        // 转换
        warehouse = createRequest.transform();
        // 保存
        this.save(warehouse);
    }
}
