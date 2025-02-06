package io.github.thebesteric.project.intelligent.core.service.crm;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.ScoreSettingsUpdateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreSettingsResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.ScoreSettings;

public interface ScoreSettingsService extends IBaseService<ScoreSettings> {

    /**
     * 积分设置-详情
     *
     * @return ScoreSettingsResponse
     *
     * @author wangweijun
     * @since 2025/1/18 09:47
     */
    ScoreSettingsResponse details();

    /**
     * 积分设置-更新
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/21 15:25
     */
    void update(ScoreSettingsUpdateRequest updateRequest);
}
