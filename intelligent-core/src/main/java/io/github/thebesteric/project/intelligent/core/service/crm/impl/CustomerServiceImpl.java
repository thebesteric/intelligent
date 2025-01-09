package io.github.thebesteric.project.intelligent.core.service.crm.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.framework.agile.commons.util.MapWrapper;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.constant.crm.AccountType;
import io.github.thebesteric.project.intelligent.core.constant.crm.RegisterSource;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.exception.InvalidDataException;
import io.github.thebesteric.project.intelligent.core.mapper.crm.CustomerMapper;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerAuditRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSubAccountCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.CustomerResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.common.SeedService;
import io.github.thebesteric.project.intelligent.core.service.crm.CustomerService;
import io.github.thebesteric.project.intelligent.core.util.BCryptUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * CustomerServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:54:20
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    private final SeedService seedService;

    private final DataNotFoundException customerNotFoundException = new DataNotFoundException("客户不存在");

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
    @Override
    public PagingResponse<CustomerResponse> page(CustomerSearchRequest searchRequest, RegisterSource registerSource, List<AuditStatus> auditStatuses) {
        IPage<Customer> page = this.lambdaQuery()
                .eq(registerSource != null, Customer::getRegisterSource, registerSource)
                .in(CollectionUtils.isNotEmpty(auditStatuses), Customer::getAuditStatus, auditStatuses)
                .eq(searchRequest.isShowSubAccountOnly(), Customer::getAccountType, AccountType.SLAVE)
                .and(StringUtils.isNotBlank(searchRequest.getKeyword()), query ->
                        query.like(Customer::getName, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getSerialNo, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getKeyword, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getPhone, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getUsername, searchRequest.getKeyword()))
                .orderByDesc(Customer::getSerialNo)
                .orderByAsc(Customer::getCreatedAt)
                .page(searchRequest.getPage(Customer.class));
        List<CustomerResponse> records = page.getRecords().stream().map(r -> (CustomerResponse) new CustomerResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 创建客户
     *
     * @param createRequest 请求
     * @param auditStatus   审核状态
     *
     * @author wangweijun
     * @since 2024/12/12 18:57
     */
    @Override
    public void create(CustomerCreateRequest createRequest, AuditStatus auditStatus) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Map<String, Object> queryParams = MapWrapper.createLambda(Customer.class)
                .put("tenant_id", tenantId)
                .put(Customer::getUsername, createRequest.getUsername()).build();
        Customer customer = this.getByParams(queryParams);

        // 数据校验
        DataValidator.create(BizException.class)
                .validate(customer != null, customerNotFoundException);

        customer = createRequest.transform();
        customer.setPassword(BCryptUtils.encode(createRequest.getPassword()));
        customer.setAuditStatus(auditStatus);

        // 客户拼音码（关键字）
        if (StringUtils.isBlank(customer.getKeyword())) {
            String keyword = PinyinUtil.getFirstLetter(customer.getName(), "");
            customer.setKeyword(keyword);
        }
        // 客户编号
        if (StringUtils.isBlank(customer.getSerialNo())) {
            String serialNo = seedService.getAndIncrement(customer.getTenantId(), SeedType.CUSTOMER_SERIAL_NO);
            customer.setSerialNo(serialNo);
        }
        this.save(customer);
    }

    /**
     * 客户审核
     *
     * @param auditRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/24 18:50
     */
    @Override
    public void audit(CustomerAuditRequest auditRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Customer customer = this.getByTenantAndId(tenantId, auditRequest.getCustomerId());
        DataValidator.create(BizException.class)
                .validate(customer == null, customerNotFoundException)
                .validate(Objects.requireNonNull(customer).getAuditStatus() != AuditStatus.WAIT_AUDIT, new InvalidDataException("客户已完成审核"));
        // 保存审核信息
        customer.setCustomerAuditInfo(auditRequest);
        this.updateById(customer);
    }

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
    @Override
    public CustomerResponse detail(Long id) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Customer customer = this.getByTenantAndId(tenantId, id);
        DataValidator.create(BizException.class)
                .validate(customer == null, customerNotFoundException);
        return (CustomerResponse) new CustomerResponse().transform(customer);
    }

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
    @Override
    public Customer getByUsername(String tenantId, String username) {
        return this.lambdaQuery().eq(Customer::getTenantId, tenantId)
                .eq(Customer::getUsername, username)
                .one();
    }

    /**
     * 锁定用户
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/7 13:33
     */
    @Override
    public void lock(Long customerId) {
        switchCustomerStatus(customerId, customer -> {
            if (!customer.isLock()) {
                customer.setLock(true);
                this.updateById(customer);
            }
        });
    }

    /**
     * 解锁用户
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/7 13:34
     */
    @Override
    public void unlock(Long customerId) {
        switchCustomerStatus(customerId, customer -> {
            if (customer.isLock()) {
                customer.setLock(false);
                this.updateById(customer);
            }
        });
    }

    /**
     * 订单提交-开启
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/8 10:56
     */
    @Override
    public void orderSubmitEnable(Long customerId) {
        switchCustomerStatus(customerId, customer -> {
            if (!customer.isEnableSubmitOrder()) {
                customer.setEnableSubmitOrder(true);
                this.updateById(customer);
            }
        });
    }

    /**
     * 订单提交-关闭
     *
     * @param customerId 客户 ID
     *
     * @author wangweijun
     * @since 2025/1/8 10:56
     */
    @Override
    public void orderSubmitDisable(Long customerId) {
        switchCustomerStatus(customerId, customer -> {
            if (customer.isEnableSubmitOrder()) {
                customer.setEnableSubmitOrder(false);
                this.updateById(customer);
            }
        });
    }

    /**
     * 设置客户状态
     *
     * @param customerId 客户 ID
     * @param consumer   逻辑
     *
     * @author wangweijun
     * @since 2025/1/8 11:25
     */
    private void switchCustomerStatus(Long customerId, Consumer<Customer> consumer) {
        Processor.prepare()
                .start(() -> {
                    String tenantId = SecurityUtils.getTenantIdWithException();
                    return getByTenantAndId(tenantId, customerId);
                })
                .validate(customer -> {
                    if (customer == null) {
                        throw customerNotFoundException;
                    }
                })
                .complete(consumer);
    }

    /**
     * 添加子账户
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/7 16:53
     */
    @Override
    public void subAccountCreate(CustomerSubAccountCreateRequest createRequest) {
        String tenantId = createRequest.getTenantId();
        String username = createRequest.getUsername();
        Processor.prepare()
                .start(() -> {
                    Long customerId = createRequest.getCustomerId();
                    return this.getByTenantAndId(tenantId, customerId);
                })
                .validate(owner -> {
                    if (owner == null) {
                        throw customerNotFoundException;
                    }
                    if (!owner.isEnableSubAccount()) {
                        throw new BizException("用户未开启子账号权限");
                    }
                    Customer subAccount = this.getByUsername(tenantId, username);
                    if (subAccount != null) {
                        throw new InvalidDataException("用户名已存在");
                    }
                })
                .next(owner -> {
                    String password = createRequest.getPassword();
                    String name = createRequest.getName();
                    return owner.createSubAccount(username, password, name);
                })
                .complete(this::save);
    }
}
