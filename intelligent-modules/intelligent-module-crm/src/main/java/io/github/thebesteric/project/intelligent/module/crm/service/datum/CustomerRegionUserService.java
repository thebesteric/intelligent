package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUserSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionUserResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRegionUser;

import java.util.List;

public interface CustomerRegionUserService extends IBaseService<CustomerRegionUser> {

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerRegionUserResponse>
     *
     * @author wangweijun
     * @since 2024/12/30 18:20
     */
    PagingResponse<CustomerRegionUserResponse> page(CustomerRegionUserSearchRequest searchRequest);

    /**
     * 根据区域 ID 查询区域用户
     *
     * @param tenantId 租户 ID
     * @param regionId 区域 ID
     *
     * @return List<CustomerRegionUser>
     *
     * @author wangweijun
     * @since 2024/12/30 17:30
     */
    List<CustomerRegionUser> findByRegionId(String tenantId, Long regionId);
}
