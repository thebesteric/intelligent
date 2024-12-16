package io.github.thebesteric.project.intelligent.module.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.CustomerMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.request.CustomerCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.Customer;
import io.github.thebesteric.project.intelligent.module.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Customer customer = createRequest.transform(Customer.class);
        this.save(customer);
    }

}
