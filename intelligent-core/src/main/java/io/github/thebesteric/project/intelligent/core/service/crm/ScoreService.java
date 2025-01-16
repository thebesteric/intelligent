package io.github.thebesteric.project.intelligent.core.service.crm;

import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.*;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInDetailResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreDetailResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Score;

/**
 * 积分服务
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:05:49
 */
public interface ScoreService extends IBaseService<Score> {

    /**
     * 分页列表
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<ScoreResponse>
     *
     * @author wangweijun
     * @since 2024/1/14 10:39
     */
    PagingResponse<ScoreTotalResponse> page(ScoreTotalSearchRequest searchRequest);

    /**
     * 积分明细
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<ScoreResponse>
     *
     * @author wangweijun
     * @since 2024/1/14 10:39
     */
    PagingResponse<ScoreDetailResponse> detail(ScoreDetailSearchRequest searchRequest);

    /**
     * 积分调整
     *
     * @param adjustRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/16 14:08
     */
    void adjust(ScoreAdjustRequest adjustRequest);

    /**
     * 签到积分列表
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<ScoreCheckInTotalResponse>
     *
     * @author wangweijun
     * @since 2025/1/16 15:20
     */
    PagingResponse<ScoreCheckInTotalResponse> checkInPage(ScoreCheckInTotalSearchRequest searchRequest);

    /**
     * 签到积分明细
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<ScoreCheckInDetailResponse>
     *
     * @author wangweijun
     * @since 2025/1/16 16:40
     */
    PagingResponse<ScoreCheckInDetailResponse> checkInDetail(ScoreCheckInDetailSearchRequest searchRequest);
}
