package io.github.thebesteric.project.intelligent.core.service.crm;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.github.thebesteric.project.intelligent.core.constant.crm.RegisterSource;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerAuditRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSubAccountCreateRequest;
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

    /**
     * 锁定用户
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/7 13:33
     */
    void lock(Long customerId);

    /**
     * 解锁用户
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/7 13:34
     */
    void unlock(Long customerId);

    /**
     * 订单提交-开启
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/8 10:56
     */
    void orderSubmitEnable(Long customerId);

    /**
     * 订单提交-关闭
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/8 10:56
     */
    void orderSubmitDisable(Long customerId);

    /**
     * 添加子账户
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/7 16:53
     */
    void subAccountCreate(CustomerSubAccountCreateRequest createRequest);
}
