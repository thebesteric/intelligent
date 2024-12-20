package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerDiscountInfoSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerDiscountInfo;

public interface CustomerDiscountInfoService extends IBaseService<CustomerDiscountInfo> {
    /**
     * 折扣设置
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/19 16:31
     */
    void discountSettings(CustomerDiscountInfoSettingsRequest settingsRequest);
}
