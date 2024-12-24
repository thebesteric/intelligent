package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTypePurchaseAuthSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTypePurchaseAuthResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTypePurchaseAuth;

public interface CustomerTypePurchaseAuthService extends IBaseService<CustomerTypePurchaseAuth> {

    /**
     * 采购授权设置
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/24 09:49
     */
    void purchaseAuthSettings(CustomerTypePurchaseAuthSettingsRequest settingsRequest);

    /**
     * 根据客户类型获取采购授权设置
     *
     * @param tenantId       租户 ID
     * @param customerTypeId 客户类型 ID
     *
     * @return CustomerTypePurchaseAuth
     *
     * @author wangweijun
     * @since 2024/12/24 11:14
     */
    CustomerTypePurchaseAuth getByCustomerTypeId(String tenantId, Long customerTypeId);

    /**
     * 根据客户类型获取采购授权设置
     *
     * @param customerTypeId 客户类型 ID
     *
     * @return CustomerTypePurchaseAuthResponse
     *
     * @author wangweijun
     * @since 2024/12/24 11:23
     */
    CustomerTypePurchaseAuthResponse purchaseAuthGet(Long customerTypeId);
}
