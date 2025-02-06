package io.github.thebesteric.project.intelligent.modules.common.service.crm;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidAdjustRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidDetailsRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request.PrepaidSearchRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response.PrepaidDetailsResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response.PrepaidSearchResponse;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.crm.Prepaid;

public interface PrepaidService {

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
    PagingResponse<PrepaidSearchResponse> page(PrepaidSearchRequest searchRequest);

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
    PagingResponse<PrepaidDetailsResponse> details(PrepaidDetailsRequest detailsRequest);

    /**
     * 预存款调整
     *
     * @param adjustRequest 请求
     *
     * @author wangweijun
     * @since 2025/2/5 10:17
     */
    void adjust(PrepaidAdjustRequest adjustRequest);

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
    Prepaid getByLatest(String tenantId, Long customerId);
}
