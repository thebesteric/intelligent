package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRelationAlarmUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRelationAlarmResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRelationAlarm;

/**
 * CustomerRelationService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:45:29
 */
public interface CustomerRelationAlarmService extends IBaseService<CustomerRelationAlarm> {

    /**
     * 分页列表
     *
     * @param request 请求
     *
     * @author wangweijun
     * @since 2024/12/26 10:50
     */
    PagingResponse<CustomerRelationAlarmResponse> page(CustomerRelationAlarmSearchRequest request);

    /**
     * 创建客勤预警
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/26 10:49
     */
    void create(CustomerRelationAlarmCreateRequest createRequest);

    /**
     * 更新客勤预警
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/26 13:33
     */
    void update(CustomerRelationAlarmUpdateRequest updateRequest);
}
