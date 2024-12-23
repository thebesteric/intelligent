package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.constant.DiscountType;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerDiscountInfoSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.BrandDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CatalogDiscountInfoResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerDiscountInfo;

import java.util.List;

public interface CustomerDiscountInfoService extends IBaseService<CustomerDiscountInfo> {
    /**
     * 折扣设置
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/19 16:31
     */
    void discountInfoSettings(CustomerDiscountInfoSettingsRequest settingsRequest);

    /**
     * 品牌折扣信息
     *
     * @param customerLevelId 会员等级 ID
     * @param keyword         名称检索
     *
     * @return List<BrandDiscountInfoResponse>
     *
     * @author wangweijun
     * @since 2024/12/20 16:58
     */
    List<BrandDiscountInfoResponse> discountInfoBrand(Long customerLevelId, String keyword);

    /**
     * 目录折扣信息
     *
     * @param customerLevelId 会员等级 ID
     *
     * @return List<BrandDiscountInfoResponse>
     *
     * @author wangweijun
     * @since 2024/12/23 15:25
     */
    List<CatalogDiscountInfoResponse> discountInfoCatalog(Long customerLevelId);

    /**
     * 获取客户折扣信息
     *
     * @param tenantId         租户 ID
     * @param customerLevelId  等级 ID
     * @param discountType     折扣类型
     * @param discountObjectId 折扣对象 ID
     *
     * @return CustomerDiscountInfo
     *
     * @author wangweijun
     * @since 2024/12/20 18:00
     */
    CustomerDiscountInfo getUnique(String tenantId, Long customerLevelId, DiscountType discountType, Long discountObjectId);
}
