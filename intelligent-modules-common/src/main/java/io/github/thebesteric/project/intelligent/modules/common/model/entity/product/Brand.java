package io.github.thebesteric.project.intelligent.modules.common.model.entity.product;

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
 * 品牌
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 15:25:42
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.Product.TABLE_NAME_PREFIX + "brand", autoResultMap = true)
@EntityClass(comment = "商品品牌", schemas = ApplicationConstants.DataSource.INTELLIGENT_MODULE_PRODUCT)
public class Brand extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 8671583095593232559L;

    @EntityColumn(length = 64, nullable = false, comment = "全名")
    private String name;

    @EntityColumn(length = 128, nullable = false, comment = "别名")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> alias;

    @EntityColumn(length = 64, nullable = false, comment = "关键字")
    private String keyword;

    @EntityColumn(length = 256, comment = "官网")
    private String website;

    @EntityColumn(nullable = false, comment = "排序")
    private Integer seq = 0;

    @EntityColumn(comment = "图标 ID")
    private Long iconImageId;


}
