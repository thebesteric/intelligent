package io.github.thebesteric.project.intelligent.core.mapper.crm;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;

/**
 * CustomerMapper
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 13:57:36
 */
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public interface CustomerMapper extends IBaseMapper<Customer> {
}
