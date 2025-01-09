package io.github.thebesteric.project.intelligent.module.crm.service.settings;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.constant.AgreementType;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.request.AgreementSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.response.AgreementResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.settings.Agreement;

public interface AgreementService extends IBaseService<Agreement> {

    /**
     * 设置协议
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/8 12:59
     */
    void settings(AgreementSettingsRequest settingsRequest);

    /**
     * 获取协议
     *
     * @param type 协议类型
     *
     * @return AgreementResponse
     *
     * @author wangweijun
     * @since 2025/1/9 17:46
     */
    AgreementResponse detail(AgreementType type);
}
