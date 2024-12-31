package io.github.thebesteric.project.intelligent.core.base;

import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public abstract class BaseTenantBizEntity extends BaseBizEntity {
    @Serial
    private static final long serialVersionUID = 6034991749333824043L;

    @EntityColumn(length = 64, nullable = false, comment = "租户 ID", sequence = Integer.MIN_VALUE)
    protected String tenantId;
}