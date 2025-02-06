package io.github.thebesteric.project.intelligent.modules.common.mapper.crm;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.crm.Prepaid;

/**
 * 预存款
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-23 12:03:49
 */
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public interface PrepaidMapper extends IBaseMapper<Prepaid> {
}
