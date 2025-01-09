package io.github.thebesteric.project.intelligent.module.crm.model.entity.settings;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.module.crm.constant.AgreementType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * 协议
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-08 11:42:07
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "agreement")
@EntityClass(comment = "协议")
public class Agreement extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -3379755017635617273L;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, comment = "协议类型")
    private AgreementType type;

    @EntityColumn(comment = "协议内容")
    private String content;
}
