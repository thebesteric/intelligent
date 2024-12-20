package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.module.crm.constant.DiscountType;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerDiscountInfoMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerDiscountInfoSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerDiscountInfo;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerDiscountInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
    /**
     * 折扣设置
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/19 16:31
     */
    @Override
    public void discountSettings(CustomerDiscountInfoSettingsRequest settingsRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        // 删除所有该等级的折扣信息
        CustomerDiscountInfoMapper discountInfoMapper = this.getBaseMapper();
        discountInfoMapper.physicalDeleteByColumns(CustomerDiscountInfo.class, Map.of("tenant_id", tenantId, "customer_level_id", settingsRequest.getCustomerLevelId()));
        // 添加新的折扣信息
        Optional.ofNullable(settingsRequest.getDiscountInfos())
                .filter(l -> !l.isEmpty())
                .ifPresent(l -> l.forEach(discountInfo -> {
                    Long customerLevelId = settingsRequest.getCustomerLevelId();
                    DiscountType discountType = settingsRequest.getDiscountType();
                    Long discountObjectId = discountInfo.getDiscountObjectId();
                    Double discountRate = discountInfo.getDiscountRate();
                    CustomerDiscountInfo customerDiscountInfo = CustomerDiscountInfo.of(tenantId, customerLevelId, discountType, discountObjectId, discountRate);
                    this.save(customerDiscountInfo);
                }));
    }
}
