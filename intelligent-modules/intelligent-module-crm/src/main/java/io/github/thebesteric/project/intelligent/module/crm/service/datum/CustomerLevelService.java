package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerLevelUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerLevelResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerLevel;

/**
 * CustomerLevelService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:44:09
 */
public interface CustomerLevelService extends IBaseService<CustomerLevel> {
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
    PagingResponse<CustomerLevelResponse> page(CustomerLevelSearchRequest searchRequest);

    /**
     * 创建等级
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/17 19:15
     */
    void create(CustomerLevelCreateRequest createRequest);

    /**
     * 更新等级
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/17 19:15
     */
    void update(CustomerLevelUpdateRequest updateRequest);

    /**
     * 删除等级
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/23 20:10
     */
    void delete(Long id);
}
