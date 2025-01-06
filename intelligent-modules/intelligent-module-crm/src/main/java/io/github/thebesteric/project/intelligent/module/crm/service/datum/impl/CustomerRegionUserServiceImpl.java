package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.exception.DataExistsException;
import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.exception.InvalidDataException;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.core.UserService;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerRegionUserMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUserSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUserUpsertRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionUserResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRegionUserService;
import io.github.thebesteric.project.intelligent.core.service.crm.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * CustomerRegionUserServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 17:27:35
 */
@Service
@RequiredArgsConstructor
public class CustomerRegionUserServiceImpl extends ServiceImpl<CustomerRegionUserMapper, Customer> implements CustomerRegionUserService {

    private final UserService userService;
    private final CustomerService customerService;

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerRegionUserResponse>
     *
     * @author wangweijun
     * @since 2024/12/30 18:20
     */
    @Override
    public PagingResponse<CustomerRegionUserResponse> page(CustomerRegionUserSearchRequest searchRequest) {
        return Processor.prepare()
                .start(() -> {
                    String tenantId = SecurityUtils.getTenantIdWithException();
                    IPage<CustomerRegionUserResponse> page = searchRequest.getPage(CustomerRegionUserResponse.class);
                    return this.getBaseMapper().page(tenantId, searchRequest, page);
                })
                .interim(page -> {
                    // 处理关联业务员
                    List<CustomerRegionUserResponse> records = page.getRecords();
                    for (CustomerRegionUserResponse item : records) {
                        CustomerRegionUserResponse.CustomerSimpleResponse customer = item.getCustomer();
                        List<String> salesUserIds = customer.getSalesUserIds();
                        for (String salesUserId : salesUserIds) {
                            User salesUser = userService.getById(Long.valueOf(salesUserId));
                            customer.getSalesUsers().add(CustomerRegionUserResponse.SalesUserInfo.of(salesUser));
                        }
                    }
                })
                .complete(page -> {
                    return PagingResponse.of(page);
                });
    }

    /**
     * 添加区域关联用户
     *
     * @param addRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/31 17:40
     */
    @Transactional
    @Override
    public void addCustomers(CustomerRegionUserUpsertRequest addRequest) {
        Processor.prepare()
                .start(() -> {
                    List<Long> customerIds = addRequest.getCustomerIds();
                    return customerIds.stream().map(id -> customerService.getByTenantAndId(addRequest.getTenantId(), id)).filter(Objects::nonNull).toList();
                })
                .validate(customers -> {
                    List<Customer> hasRegions = customers.stream().filter(customer -> customer.getRegionId() != null).toList();
                    if (!hasRegions.isEmpty()) {
                        throw new InvalidDataException("客户已有区域，请先解除区域关联");
                    }
                })
                .interim(customers -> customers.forEach(customer -> customer.setRegionId(addRequest.getRegionId())))
                .complete(customers -> {
                    customers.forEach(customerService::updateById);
                });
    }

    /**
     * 移除区域关联用户
     *
     * @param removeRequest 请求
     *
     * @author wangweijun
     * @since 2024/1/2 10:34
     */
    @Override
    public void removeCustomers(CustomerRegionUserUpsertRequest removeRequest) {
        Processor.prepare(DataValidator.ExceptionThrowStrategy.COLLECT)
                .start(() -> {
                    List<Long> customerIds = removeRequest.getCustomerIds();
                    return customerIds.stream().map(id -> customerService.getByTenantAndId(removeRequest.getTenantId(), id)).filter(Objects::nonNull).toList();
                })
                .validate(customers -> {
                    if (customers.isEmpty()) {
                        throw new DataExistsException("没有需要解除关联的数据");
                    }
                })
                .next(customers -> customers.stream().filter(customer -> customer.getRegionId() != null).map(customer -> {
                    customer.setRegionId(null);
                    return customer;
                }).toList())
                .complete((customers, exceptions) -> {
                    if (exceptions.size() == 1 && (exceptions.get(0) instanceof DataExistsException)) {
                        return;
                    }
                    customers.forEach(customerService::updateById);
                });
    }

    /**
     * 根据区域 ID 查询区域用户
     *
     * @param tenantId 租户 ID
     * @param regionId 区域 ID
     *
     * @return List<CustomerRegionUser>
     *
     * @author wangweijun
     * @since 2024/12/30 17:30
     */
    @Override
    public List<Customer> findByRegionId(String tenantId, Long regionId) {
        return this.lambdaQuery().eq(Customer::getTenantId, tenantId)
                .eq(Customer::getRegionId, regionId)
                .list();
    }
}
