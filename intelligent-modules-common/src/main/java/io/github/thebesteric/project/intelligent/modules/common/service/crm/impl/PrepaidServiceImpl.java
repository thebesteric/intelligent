package io.github.thebesteric.project.intelligent.modules.common.service.crm.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.crm.CustomerService;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidType;
import io.github.thebesteric.project.intelligent.modules.common.mapper.crm.PrepaidMapper;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidAdjustRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidDetailsRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response.PrepaidDetailsResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response.PrepaidSearchResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.crm.Prepaid;
import io.github.thebesteric.project.intelligent.modules.common.service.crm.PrepaidService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PrepaidServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-23 12:48:11
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class PrepaidServiceImpl extends ServiceImpl<PrepaidMapper, Prepaid> implements PrepaidService {

    private final CustomerService customerService;

    /**
     * 预存款列表
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<PrepaidSearchResponse>
     *
     * @author wangweijun
     * @since 2025/2/5 16:11
     */
    @Override
    public PagingResponse<PrepaidSearchResponse> page(PrepaidSearchRequest searchRequest) {
        return Processor.prepare()
                .start(() -> customerService.lambdaQuery()
                        .eq(Customer::getTenantId, SecurityUtils.getTenantId())
                        .and(StringUtils.isNotBlank(searchRequest.getKeyword()), query -> query.like(Customer::getName, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getSerialNo, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getUsername, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getPhone, searchRequest.getKeyword())
                                .or()
                                .like(Customer::getKeyword, searchRequest.getKeyword()))
                        .page(searchRequest.getPage(Customer.class)))
                .complete(customerPage -> {
                    List<PrepaidSearchResponse> responses = new ArrayList<>();
                    List<Customer> customers = customerPage.getRecords();
                    for (Customer customer : customers) {
                        Prepaid latestPrepaid = getByLatest(customer.getTenantId(), customer.getId());
                        PrepaidSearchResponse response = PrepaidSearchResponse.of(customer, latestPrepaid);
                        responses.add(response);
                    }
                    return PagingResponse.of(customerPage.getCurrent(), customerPage.getSize(), customerPage.getTotal(), responses);
                });
    }

    /**
     * 预存款明细
     *
     * @param detailsRequest 请求
     *
     * @return PagingResponse<PrepaidDetailsResponse>
     *
     * @author wangweijun
     * @since 2025/2/6 10:04
     */
    @Override
    public PagingResponse<PrepaidDetailsResponse> details(PrepaidDetailsRequest detailsRequest) {
        return Processor.prepare()
                .start(() -> {
                    Date startDate = detailsRequest.getStartDate();
                    Date endDate = detailsRequest.getEndDate();
                    return this.lambdaQuery()
                            .eq(Prepaid::getTenantId, SecurityUtils.getTenantIdWithException())
                            .eq(Prepaid::getCustomerId, detailsRequest.getCustomerId())
                            .eq(detailsRequest.getType() != null, Prepaid::getType, detailsRequest.getType())
                            .eq(detailsRequest.getChangeProject() != null, Prepaid::getChangeProject, detailsRequest.getChangeProject())
                            .ge(startDate != null && endDate == null, Prepaid::getCreatedAt, startDate)
                            .le(startDate == null && endDate != null, Prepaid::getCreatedAt, endDate)
                            .between(startDate != null && endDate != null, Prepaid::getCreatedAt, startDate, endDate)
                            .orderByDesc(Prepaid::getCreatedAt)
                            .page(detailsRequest.getPage(Prepaid.class));
                })
                .complete(page -> {
                    List<Prepaid> records = page.getRecords();
                    List<PrepaidDetailsResponse> list = records.stream().map(i -> {
                        PrepaidDetailsResponse response = new PrepaidDetailsResponse().transform(i);
                        response.setType(i.getType().toMap());
                        response.setChangeProject(i.getChangeProject().toMap());
                        return response;
                    }).toList();
                    return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), list);
                });
    }

    /**
     * 预存款调整
     *
     * @param adjustRequest 请求
     *
     * @author wangweijun
     * @since 2025/2/5 10:17
     */
    @Override
    public void adjust(PrepaidAdjustRequest adjustRequest) {
        Processor.prepare()
                .start(() -> {
                    String tenantId = adjustRequest.getTenantId();
                    Long customerId = adjustRequest.getCustomerId();
                    return customerService.getByTenantAndId(tenantId, customerId);
                })
                .validate(customer -> {
                    if (customer == null) {
                        throw new DataNotFoundException("客户不存在");
                    }
                })
                // 获取最新的一条余额记录
                .next(customer -> {
                    String tenantId = customer.getTenantId();
                    Long customerId = customer.getId();
                    return getByLatest(tenantId, customerId);
                })
                // 保存当前调整的余额记录
                .complete(latestPrepaid -> {
                    String tenantId = adjustRequest.getTenantId();
                    Long customerId = adjustRequest.getCustomerId();
                    PrepaidType prepaidType = adjustRequest.getType();
                    BigDecimal changeAmount = adjustRequest.getChangeAmount();
                    String desc = adjustRequest.getDesc();
                    Prepaid prepaid = Prepaid.convertToAdjust(tenantId, customerId, prepaidType, changeAmount, desc, latestPrepaid);
                    save(prepaid);
                });
    }

    /**
     * 获取最新的一条余额记录
     *
     * @param tenantId   租户 ID
     * @param customerId 客户 ID
     *
     * @return Prepaid
     *
     * @author wangweijun
     * @since 2025/2/5 11:54
     */
    @Override
    public Prepaid getByLatest(String tenantId, Long customerId) {
        return this.lambdaQuery().eq(Prepaid::getTenantId, tenantId)
                .eq(Prepaid::getCustomerId, customerId)
                .orderByDesc(Prepaid::getCreatedAt)
                .last("limit 1")
                .one();
    }
}
