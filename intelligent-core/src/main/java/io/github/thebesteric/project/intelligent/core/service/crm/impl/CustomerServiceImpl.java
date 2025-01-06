package io.github.thebesteric.project.intelligent.core.service.crm.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.framework.agile.commons.util.MapWrapper;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.constant.crm.RegisterSource;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.exception.InvalidDataException;
import io.github.thebesteric.project.intelligent.core.mapper.crm.CustomerMapper;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerAuditRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerSearchRequest;
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
                .validate(customer != null, new DataAlreadyExistsException("用户名已存在"));

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
                .validate(customer == null, new DataNotFoundException("客户不存在"))
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
                .validate(customer == null, new DataNotFoundException("客户不存在"));
        return (CustomerResponse) new CustomerResponse().transform(customer);
    }
}
