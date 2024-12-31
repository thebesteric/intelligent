package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerTypeMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTypeResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerType;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CustomerTypeServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:54:41
 */
@Service
@RequiredArgsConstructor
public class CustomerTypeServiceImpl extends ServiceImpl<CustomerTypeMapper, CustomerType> implements CustomerTypeService {
    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerTypeResponse>
     *
     * @author wangweijun
     * @since 2024/12/18 16:14
     */
    @Override
    public PagingResponse<CustomerTypeResponse> page(CustomerTypeSearchRequest searchRequest) {
        IPage<CustomerType> page = this.lambdaQuery()
                .and(StringUtils.isNotBlank(searchRequest.getName()), query ->
                        query.like(CustomerType::getName, searchRequest.getName())
                                .or()
                                .like(CustomerType::getKeyword, searchRequest.getName()))
                .page(searchRequest.getPage(CustomerType.class));
        List<CustomerTypeResponse> records = page.getRecords().stream().map(r -> (CustomerTypeResponse) new CustomerTypeResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 创建客户类型
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/23 18:21
     */
    @Transactional
    @Override
    public void create(CustomerTypeCreateRequest createRequest) {
        String tenantId = createRequest.getTenantId();
        String name = createRequest.getName();
        CustomerType customerType = this.lambdaQuery().eq(CustomerType::getTenantId, tenantId)
                .eq(CustomerType::getName, name)
                .eq(CustomerType::getState, 1).one();
        if (customerType != null) {
            throw new DataAlreadyExistsException("客户类型已存在");
        }
        // 转换
        customerType = createRequest.transform();
        // 是否默认设置
        setOthersDefaultFalseIfNecessary(tenantId, customerType.getIsDefault());
        // 保存
        this.save(customerType);
    }

    /**
     * 更新客户类型
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/23 19:50
     */
    @Override
    public void update(CustomerTypeUpdateRequest updateRequest) {
        Processor.prepare()
                .start(() -> this.findByName(updateRequest.getTenantId(), updateRequest.getName()))
                .validate(sameNames -> {
                    if (sameNames.isEmpty()) {
                        return;
                    }
                    if (sameNames.size() == 1) {
                        CustomerType maybeSelf = sameNames.get(0);
                        if (maybeSelf.getId().equals(updateRequest.getId())) {
                            return;
                        }
                    }
                    throw new DataAlreadyExistsException("类型名称重复");
                })
                .next(sameNames -> {
                    CustomerType customerType = sameNames.get(0);
                    updateRequest.merge(customerType);
                    setOthersDefaultFalseIfNecessary(customerType.getTenantId(), customerType.getIsDefault());
                    return customerType;
                })
                .complete(this::updateById);
    }

    /**
     * 删除客户类型
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/23 20:25
     */
    @Override
    public void delete(Long id) {
        this.getBaseMapper().physicalDeleteById(CustomerType.class, id);
    }

    /**
     * 根据名称获取客户类型
     *
     * @param tenantId 租户 ID
     * @param name     名称
     *
     * @return List<CustomerRelationAlarm>
     *
     * @author wangweijun
     * @since 2024/12/26 13:38
     */
    private List<CustomerType> findByName(String tenantId, String name) {
        return this.lambdaQuery().eq(CustomerType::getTenantId, tenantId)
                .eq(CustomerType::getName, name)
                .list();
    }

    /**
     * 如果是默认，则将其他默认设置为 false
     *
     * @param tenantId  租户 ID
     * @param isDefault 是否默认
     *
     * @author wangweijun
     * @since 2024/12/23 20:22
     */
    private void setOthersDefaultFalseIfNecessary(String tenantId, boolean isDefault) {
        if (isDefault) {
            this.lambdaUpdate().eq(CustomerType::getTenantId, tenantId).eq(CustomerType::getIsDefault, true)
                    .set(CustomerType::getIsDefault, false)
                    .update();
        }
    }
}
