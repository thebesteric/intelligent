package io.github.thebesteric.project.intelligent.modules.common.model.entity.stock;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.mapper.handler.CommaStringToListTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.util.List;

/**
 * 仓库
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 14:12:07
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.Stock.TABLE_NAME_PREFIX + "warehouse", autoResultMap = true)
@EntityClass(comment = "仓库", schemas = ApplicationConstants.Application.Module.Stock.DATASOURCE_INTELLIGENT_MODULE_STOCK)
public class Warehouse extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 1224172423693978119L;

    @EntityColumn(length = 32, nullable = false, comment = "仓库编号")
    private String code;

    @EntityColumn(length = 64, nullable = false, comment = "仓库名称")
    private String name;

    @EntityColumn(nullable = false, comment = "排序")
    private Integer seq = 0;

    @EntityColumn(comment = "省市区 ID")
    private Long areaId;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "覆盖区域 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> coverRegionIds;

    @EntityColumn(length = 255, comment = "地址")
    private String address;

    @EntityColumn(length = 64, comment = "联系人")
    private String contact;

    @EntityColumn(length = 64, comment = "联系电话")
    private String phone;
}
