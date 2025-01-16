package io.github.thebesteric.project.intelligent.core.service.crm.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.crm.ConsumeType;
import io.github.thebesteric.project.intelligent.core.constant.crm.ScoreType;
import io.github.thebesteric.project.intelligent.core.mapper.crm.ScoreMapper;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.*;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInDetailResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreDetailResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Score;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.crm.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ScoreServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:06:45
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

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
    @Override
    public PagingResponse<ScoreTotalResponse> page(ScoreTotalSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        IPage<ScoreTotalResponse> page = searchRequest.getPage(ScoreTotalResponse.class);
        page = this.getBaseMapper().page(tenantId, searchRequest, page);
        return PagingResponse.of(page);
    }

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
    @Override
    public PagingResponse<ScoreDetailResponse> detail(ScoreDetailSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Date startDate = searchRequest.getStartDate();
        Date endDate = searchRequest.getEndDate();
        IPage<Score> page = this.lambdaQuery()
                .eq(Score::getTenantId, tenantId)
                .eq(Score::getCustomerId, searchRequest.getCustomerId())
                .eq(searchRequest.getScoreType() != null, Score::getScoreType, searchRequest.getScoreType())
                .eq(searchRequest.getConsumeType() != null, Score::getConsumeType, searchRequest.getConsumeType())
                .gt(startDate != null && endDate == null, Score::getCreatedAt, startDate)
                .lt(startDate == null && endDate != null, Score::getCreatedAt, endDate)
                .between(startDate != null && endDate != null, Score::getCreatedAt, startDate, endDate)
                .orderByDesc(Score::getCreatedAt)
                .page(searchRequest.getPage(Score.class));
        List<Score> records = page.getRecords();
        List<ScoreDetailResponse> scoreDetailResponses = records.stream().map(r -> (ScoreDetailResponse) new ScoreDetailResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), scoreDetailResponses);
    }

    /**
     * 积分调整
     *
     * @param adjustRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/16 14:08
     */
    @Transactional
    @Override
    public void adjust(ScoreAdjustRequest adjustRequest) {
        Processor.prepare()
                .interim(() -> {
                    if (ScoreType.ADD == adjustRequest.getScoreType()) {
                        adjustRequest.setChangeScore(adjustRequest.getChangeScore().abs());
                    } else {
                        adjustRequest.setChangeScore(adjustRequest.getChangeScore().abs().negate());
                    }
                })
                .next(() -> adjustRequest.getCustomerIds().stream().map(customerId -> {
                    Score score = adjustRequest.transform();
                    score.setCustomerId(customerId);
                    score.setConsumeType(ConsumeType.MANUAL_ADJUST);
                    return score;
                }).toList())
                .complete(scoreList -> {
                    scoreList.forEach(this::save);
                });
    }

    /**
     * 签到积分统计列表
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<ScoreCheckInTotalResponse>
     *
     * @author wangweijun
     * @since 2025/1/16 15:20
     */
    @Override
    public PagingResponse<ScoreCheckInTotalResponse> checkInPage(ScoreCheckInTotalSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        IPage<ScoreCheckInTotalResponse> page = searchRequest.getPage(ScoreCheckInTotalResponse.class);
        page = this.getBaseMapper().checkInPage(tenantId, searchRequest, page);
        return PagingResponse.of(page);
    }

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
    @Override
    public PagingResponse<ScoreCheckInDetailResponse> checkInDetail(ScoreCheckInDetailSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Date startDate = searchRequest.getStartDate();
        Date endDate = searchRequest.getEndDate();
        IPage<Score> page = this.lambdaQuery()
                .eq(Score::getTenantId, tenantId)
                .eq(Score::getCustomerId, searchRequest.getCustomerId())
                .eq(Score::getConsumeType, ConsumeType.CHECK_IN)
                .gt(startDate != null && endDate == null, Score::getCreatedAt, startDate)
                .lt(startDate == null && endDate != null, Score::getCreatedAt, endDate)
                .between(startDate != null && endDate != null, Score::getCreatedAt, startDate, endDate)
                .orderByDesc(Score::getCreatedAt)
                .page(searchRequest.getPage(Score.class));
        List<Score> records = page.getRecords();
        List<ScoreCheckInDetailResponse> scoreDetailResponses = records.stream().map(r -> (ScoreCheckInDetailResponse) new ScoreCheckInDetailResponse().transform(r)).toList();
        return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), scoreDetailResponses);
    }
}
