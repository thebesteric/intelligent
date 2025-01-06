package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagGroupResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTagGroup;

/**
 * 客户标签组
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 12:00:29
 */
public interface CustomerTagGroupService extends IBaseService<CustomerTagGroup> {

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerTagGroupResponse>
     *
     * @author wangweijun
     * @since 2024/12/27 13:30
     */
    PagingResponse<CustomerTagGroupResponse> page(CustomerTagGroupSearchRequest searchRequest);

    /**
     * 创建标签组
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 14:20
     */
    void create(CustomerTagGroupCreateRequest createRequest);

    /**
     * 更新标签组
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 15:44
     */
    void update(CustomerTagGroupUpdateRequest updateRequest);

    /**
     * 删除标签组
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/27 15:45
     */
    void delete(Long id);

}
