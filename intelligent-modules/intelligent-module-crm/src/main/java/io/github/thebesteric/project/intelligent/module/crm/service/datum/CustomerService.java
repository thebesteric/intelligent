package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.Customer;
import io.github.thebesteric.project.intelligent.modules.common.constant.AuditStatus;

/**
 * CustomerService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:43:04
 */
public interface CustomerService extends IBaseService<Customer> {

    /**
     * 创建客户
     *
     * @param createRequest 请求
     * @param auditStatus   审核状态
     *
     * @author wangweijun
     * @since 2024/12/12 18:57
     */
    void create(CustomerCreateRequest createRequest, AuditStatus auditStatus);
}
