package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.MapWrapper;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.common.SeedService;
import io.github.thebesteric.project.intelligent.core.util.BCryptUtils;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.Customer;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * CustomerServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:54:20
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    private final SeedService seedService;

    /**
     * 创建客户
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/12 18:57
     */
    @Override
    public void create(CustomerCreateRequest createRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Map<String, Object> queryParams = MapWrapper.createLambda(Customer.class)
                .put("tenant_id", tenantId)
                .put(Customer::getUsername, createRequest.getUsername()).build();
        Customer customer = this.getByParams(queryParams);
        if (customer != null) {
            throw new BizException(BizException.BizCode.DATA_ALREADY_EXISTS, "用户名已存在");
        }
        customer = createRequest.transform();
        customer.setPassword(BCryptUtils.encode(createRequest.getPassword()));
        customer.setTenantId(tenantId);

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

}
