package io.github.thebesteric.project.intelligent.oauth.service.crm;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;

/**
 * CrmUserService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-03 14:06:36
 */
public interface CrmUserService extends IBaseService<Customer> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     *
     * @return Customer
     *
     * @author wangweijun
     * @since 2025/1/3 14:30
     */
    Customer getByUsername(String username);

}
