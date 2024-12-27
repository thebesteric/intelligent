package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerRelationAlarmMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRelationAlarmResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRelationAlarm;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRelationAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerRelationServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:52:25
 */
@Service
@RequiredArgsConstructor
public class CustomerRelationAlarmServiceImpl extends ServiceImpl<CustomerRelationAlarmMapper, CustomerRelationAlarm> implements CustomerRelationAlarmService {

    private final CustomerRelationAlarmMapper customerRelationAlarmMapper;

    /**
     * 分页列表
     *
     * @param request 请求
     *
     * @author wangweijun
     * @since 2024/12/26 10:50
     */
    @Override
    public PagingResponse<CustomerRelationAlarmResponse> page(CustomerRelationAlarmSearchRequest request) {
        IPage<CustomerRelationAlarm> page = this.lambdaQuery().page(request.getPage(CustomerRelationAlarm.class));
        List<CustomerRelationAlarmResponse> records = page.getRecords().stream()
                .map(r -> (CustomerRelationAlarmResponse) new CustomerRelationAlarmResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 创建客勤预警
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/26 10:49
     */
    @Override
    public void create(CustomerRelationAlarmCreateRequest createRequest) {
        Processor.prepare(List.class).start(() -> {
                    String tenantId = SecurityUtils.getTenantIdWithException();
                    return this.findByName(tenantId, createRequest.getName());
                })
                .validate(relationAlarm -> {
                    if (relationAlarm != null) {
                        throw new DataAlreadyExistsException("预警名称重复");
                    }
                    return null;
                }).complete(relationAlarm -> {
                    CustomerRelationAlarm transform = createRequest.transform();
                    this.save(transform);
                });
    }

    /**
     * 更新客勤预警
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/26 13:33
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(CustomerRelationAlarmUpdateRequest updateRequest) {
        Processor.prepare(List.class)
                .start(() -> this.findByName(updateRequest.getTenantId(), updateRequest.getName()))
                .validate(sameNameCustomerRelationAlarms -> {
                    if (sameNameCustomerRelationAlarms.size() == 1) {
                        CustomerRelationAlarm maybeSelf = (CustomerRelationAlarm) sameNameCustomerRelationAlarms.get(0);
                        if (maybeSelf.getId().equals(updateRequest.getId())) {
                            return null;
                        }
                    }
                    return new DataAlreadyExistsException("预警名称重复");
                })
                .next(customerRelationAlarms -> getByTenantAndId(updateRequest.getTenantId(), updateRequest.getId()))
                .validate(relationAlarm -> relationAlarm == null ? new DataNotFoundException("预警不存在") : null)
                .next(updateRequest::merge)
                .complete(this::updateById);
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
    private List<CustomerRelationAlarm> findByName(String tenantId, String name) {
        return this.lambdaQuery().eq(CustomerRelationAlarm::getTenantId, tenantId)
                .eq(CustomerRelationAlarm::getName, name)
                .list();
    }
}
