package io.github.thebesteric.project.intelligent.core.service.crm;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.github.thebesteric.project.intelligent.core.constant.crm.RegisterSource;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerAuditRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.CustomerResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;

import java.util.List;

/**
 * CustomerService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:43:04
 */
public interface CustomerService extends IBaseService<Customer> {

    /**
     * 分页列表
     *
     * @param searchRequest  请求
     * @param registerSource 注册来源
     * @param auditStatuses  审核状态
     *
     * @return PagingResponse<CustomerResponse>
     *
     * @author wangweijun
     * @since 2024/12/25 09:22
     */
    PagingResponse<CustomerResponse> page(CustomerSearchRequest searchRequest, RegisterSource registerSource, List<AuditStatus> auditStatuses);

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

    /**
     * 客户审核
     *
     * @param auditRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/24 18:50
     */
    void audit(CustomerAuditRequest auditRequest);

    /**
     * 客户详情
     *
     * @param id 客户 ID
     *
     * @return CustomerResponse
     *
     * @author wangweijun
     * @since 2024/12/25 11:10
     */
    CustomerResponse detail(Long id);

    /**
     * 根据用户名获取用户
     *
     * @param tenantId 租户 ID
     * @param username 用户名
     *
     * @return Customer
     *
     * @author wangweijun
     * @since 2025/1/6 11:28
     */
    Customer getByUsername(String tenantId, String username);
}
