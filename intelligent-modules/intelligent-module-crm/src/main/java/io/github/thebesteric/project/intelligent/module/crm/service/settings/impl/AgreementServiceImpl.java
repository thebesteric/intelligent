package io.github.thebesteric.project.intelligent.module.crm.service.settings.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.service.common.SensitiveService;
import io.github.thebesteric.project.intelligent.module.crm.constant.AgreementType;
import io.github.thebesteric.project.intelligent.module.crm.mapper.settings.AgreementMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.request.AgreementSettingsRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.settings.response.AgreementResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.settings.Agreement;
import io.github.thebesteric.project.intelligent.module.crm.service.settings.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 隐私协议服务
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-08 11:55:27
 */
@Service
@RequiredArgsConstructor
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements AgreementService {

    private final SensitiveService sensitiveService;

    /**
     * 设置协议
     *
     * @param settingsRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/8 12:59
     */
    @Override
    public void settings(AgreementSettingsRequest settingsRequest) {
        Processor.prepare()
                .start(() -> {
                    Agreement agreement = getByType(settingsRequest.getType());
                    if (agreement == null) {
                        agreement = settingsRequest.transform();
                    }
                    agreement.setContent(settingsRequest.getContent());
                    return agreement;
                })
                .next(agreement -> {
                    String legalContent = sensitiveService.getResultOrElseThrow(agreement.getContent());
                    agreement.setContent(legalContent);
                    return agreement;
                })
                .complete(this::saveOrUpdate);
    }

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
    @Override
    public AgreementResponse detail(AgreementType type) {
        return Processor.prepare()
                .start(() -> this.getByType(type))
                .validate(agreement -> {
                    if (agreement == null) {
                        throw new DataNotFoundException("协议不存在");
                    }
                })
                .complete(agreement -> (AgreementResponse) new AgreementResponse().transform(agreement));
    }

    /**
     * 根据类型获取协议
     *
     * @param type 协议类型
     *
     * @return Agreement
     *
     * @author wangweijun
     * @since 2025/1/9 17:49
     */
    private Agreement getByType(AgreementType type) {
        return this.lambdaQuery().eq(Agreement::getType, type).one();
    }


}
