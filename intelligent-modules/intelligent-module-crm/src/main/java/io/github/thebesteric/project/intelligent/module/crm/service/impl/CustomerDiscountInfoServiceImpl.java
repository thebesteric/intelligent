package io.github.thebesteric.project.intelligent.module.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.CustomerDiscountInfoMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.CustomerDiscountInfo;
import io.github.thebesteric.project.intelligent.module.crm.service.CustomerDiscountInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerDiscountInfoServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:47:21
 */
@Service
@RequiredArgsConstructor
public class CustomerDiscountInfoServiceImpl extends ServiceImpl<CustomerDiscountInfoMapper, CustomerDiscountInfo> implements CustomerDiscountInfoService {
}
