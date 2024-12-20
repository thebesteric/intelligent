package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerPurchaseAuthMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerPurchaseAuth;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerPurchaseAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerPurchaseAuthServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:49:47
 */
@Service
@RequiredArgsConstructor
public class CustomerPurchaseAuthServiceImpl extends ServiceImpl<CustomerPurchaseAuthMapper, CustomerPurchaseAuth> implements CustomerPurchaseAuthService {
}
