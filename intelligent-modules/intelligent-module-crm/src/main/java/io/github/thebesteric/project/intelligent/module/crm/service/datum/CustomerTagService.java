package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTag;

import java.util.List;

/**
 * 客户标签
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 12:00:29
 */
public interface CustomerTagService extends IBaseService<CustomerTag> {

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerTagResponse>
     *
     * @author wangweijun
     * @since 2024/12/27 13:30
     */
    PagingResponse<CustomerTagResponse> page(CustomerTagSearchRequest searchRequest);

    /**
     * 创建标签
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 14:20
     */
    void create(CustomerTagCreateRequest createRequest);

    /**
     * 更新标签
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 15:44
     */
    void update(CustomerTagUpdateRequest updateRequest);

    /**
     * 删除标签
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/27 15:45
     */
    void delete(Long id);

    /**
     * 根据标签组 ID 获取标签
     *
     * @param tenantId 租户 ID
     * @param groupId  标签组 ID
     *
     * @return List<CustomerTag>
     *
     * @author wangweijun
     * @since 2024/12/27 17:36
     */
    List<CustomerTag> findByGroupId(String tenantId, Long groupId);
}
