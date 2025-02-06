package io.github.thebesteric.project.intelligent.core.mapper.crm;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.ScoreSettings;

@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public interface ScoreSettingsMapper extends IBaseMapper<ScoreSettings> {
}
