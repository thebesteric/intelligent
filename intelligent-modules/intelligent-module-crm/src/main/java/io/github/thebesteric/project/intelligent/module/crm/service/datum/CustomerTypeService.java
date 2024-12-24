package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypeUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTypeResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerType;

public interface CustomerTypeService extends IBaseService<CustomerType> {

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
    PagingResponse<CustomerTypeResponse> page(CustomerTypeSearchRequest searchRequest);

    /**
     * 创建客户类型
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/23 18:21
     */
    void create(CustomerTypeCreateRequest createRequest);

    /**
     * 更新客户类型
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/23 19:50
     */
    void update(CustomerTypeUpdateRequest updateRequest);

    /**
     * 删除客户类型
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/23 20:25
     */
    void delete(Long id);
}
