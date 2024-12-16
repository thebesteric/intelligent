package io.github.thebesteric.project.intelligent.module.crm.service;

import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.Customer;

/**
 * CustomerService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:43:04
 */
public interface CustomerService extends BaseRepositoryService<Customer> {

    /**
     * 创建客户
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/12 18:57
     */
    void create(CustomerCreateRequest createRequest);
}
