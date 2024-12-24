package io.github.thebesteric.project.intelligent.core.model.entity.common;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * Seed
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 11:37:54
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Server.CoreApi.TABLE_NAME_PREFIX + "seed")
@EntityClass(comment = "种子表", schemas = ApplicationConstants.Application.Server.CoreApi.DATASOURCE_INTELLIGENT_CORE_API)
public class Seed extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -70927475506029829L;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "类型")
    private SeedType type;

    @EntityColumn(length = 16, comment = "前缀")
    private String prefix;

    @EntityColumn(type = EntityColumn.Type.TINY_INT, nullable = false, comment = "位数")
    private Integer digits;

    @EntityColumn(nullable = false, comment = "值")
    private Long next;

    public static Seed of(String tenantId, SeedType type, Long initial) {
        Seed seed = new Seed();
        seed.tenantId = tenantId;
        seed.type = type;
        seed.prefix = type.getPrefix();
        seed.digits = type.getDigits();
        seed.next = initial;
        return seed;
    }

    public static Seed of(String tenantId, SeedType type) {
        return of(tenantId, type, 1L);
    }
}
