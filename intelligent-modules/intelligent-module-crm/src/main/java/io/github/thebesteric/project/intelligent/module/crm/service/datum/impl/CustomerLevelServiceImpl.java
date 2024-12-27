package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.MapWrapper;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.exception.InvalidDataException;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerLevelMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerLevelResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerLevel;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerLevelService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CustomerLevelServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:48:47
 */
@Service
@RequiredArgsConstructor
public class CustomerLevelServiceImpl extends ServiceImpl<CustomerLevelMapper, CustomerLevel> implements CustomerLevelService {

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerLevelResponse>
     *
     * @author wangweijun
     * @since 2024/12/18 16:14
     */
    @Override
    public PagingResponse<CustomerLevelResponse> page(CustomerLevelSearchRequest searchRequest) {
        IPage<CustomerLevel> page = this.lambdaQuery()
                .like(StringUtils.isNotBlank(searchRequest.getName()), CustomerLevel::getName, searchRequest.getName())
                .page(searchRequest.getPage(CustomerLevel.class));
        List<CustomerLevelResponse> records = page.getRecords().stream().map(r -> (CustomerLevelResponse) new CustomerLevelResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 创建等级
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/17 19:15
     */
    @Override
    public void create(CustomerLevelCreateRequest createRequest) {
        String tenantId = createRequest.getTenantId();
        Processor.prepare(CustomerLevel.class)
                .start(() -> {
                    Map<String, Object> queryParams = MapWrapper.createLambda(CustomerLevel.class, MapWrapper.KeyStyle.SNAKE_CASE)
                            .put(CustomerLevel::getTenantId, tenantId)
                            .put(CustomerLevel::getName, createRequest.getName()).build();
                    return this.getByParams(queryParams);
                })
                // 名称校验
                .validate(customerLevel -> customerLevel != null ? new DataAlreadyExistsException("等级名称重复") : null)
                // 请求转换为实体类
                .next(customerLevel -> {
                    customerLevel = createRequest.transform();
                    setOthersDefaultFalseIfNecessary(tenantId, customerLevel.getIsDefault());
                    return customerLevel;
                })
                // 校验是否开启自动升级
                .validate(customerLevel -> Boolean.TRUE.equals(customerLevel.getAutoUpgrade()) && customerLevel.getUpgradeScore() == null ? new InvalidDataException("升级积分不能为空") : null)
                // 保存
                .complete(this::save);
    }

    /**
     * 更新等级
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/17 19:15
     */
    @Override
    public void update(CustomerLevelUpdateRequest updateRequest) {
        String tenantId = updateRequest.getTenantId();
        Processor.prepare(List.class)
                .start(() -> this.findByName(tenantId, updateRequest.getName()))
                .validate(sameNameCustomerLevels -> {
                    if (sameNameCustomerLevels.size() == 1) {
                        CustomerLevel maybeSelf = (CustomerLevel) sameNameCustomerLevels.get(0);
                        if (maybeSelf.getId().equals(updateRequest.getId())) {
                            return null;
                        }
                    }
                    return new DataAlreadyExistsException("等级名称重复");
                })
                .next(sameNameCustomerLevels -> {
                    CustomerLevel customerLevel = (CustomerLevel) sameNameCustomerLevels.get(0);
                    updateRequest.merge(customerLevel);
                    setOthersDefaultFalseIfNecessary(tenantId, customerLevel.getIsDefault());
                    return customerLevel;
                })
                // 校验是否开启自动升级
                .validate(customerLevel -> Boolean.TRUE.equals(customerLevel.getAutoUpgrade()) && customerLevel.getUpgradeScore() == null ? new InvalidDataException("升级积分不能为空") : null)
                .complete(this::updateById);
    }

    /**
     * 删除等级
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/23 20:10
     */
    @Override
    public void delete(Long id) {
        this.getBaseMapper().physicalDeleteById(CustomerLevel.class, id);
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
            this.lambdaUpdate().eq(CustomerLevel::getTenantId, tenantId).eq(CustomerLevel::getIsDefault, true)
                    .set(CustomerLevel::getIsDefault, false)
                    .update();
        }
    }

    /**
     * 根据名称获取客勤预警
     *
     * @param tenantId 租户 ID
     * @param name     名称
     *
     * @return List<CustomerRelationAlarm>
     *
     * @author wangweijun
     * @since 2024/12/26 13:38
     */
    private List<CustomerLevel> findByName(String tenantId, String name) {
        return this.lambdaQuery().eq(CustomerLevel::getTenantId, tenantId)
                .eq(CustomerLevel::getName, name)
                .list();
    }
}
