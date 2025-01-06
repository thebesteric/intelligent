package io.github.thebesteric.project.intelligent.oauth.service.crm.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.mapper.crm.CustomerMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.oauth.service.crm.CrmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CrmUserServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-03 14:27:49
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class CrmUserServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CrmUserService {

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
    @Override
    public Customer getByUsername(String username) {
        return this.lambdaQuery().eq(Customer::getUsername, username).one();
    }
}
