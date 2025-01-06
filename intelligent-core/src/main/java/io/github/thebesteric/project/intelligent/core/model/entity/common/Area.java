package io.github.thebesteric.project.intelligent.core.model.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.EntityClassDomain;
import io.github.thebesteric.framework.agile.plugins.database.core.jdbc.JdbcTemplateHelper;
import io.github.thebesteric.framework.agile.plugins.database.core.listener.EntityClassCreateListener;
import io.github.thebesteric.project.intelligent.core.base.BaseEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.vavr.control.Try;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serial;
import java.util.List;

/**
 * 全国省市自治区表
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 17:27:04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.OpenApi.TABLE_NAME_PREFIX + "area")
@EntityClass(comment = "全国省市自治区表", schemas = ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public class Area extends BaseEntity implements EntityClassCreateListener {
    @Serial
    private static final long serialVersionUID = -209963749862545214L;

    @EntityColumn(length = 32, comment = "编码")
    private String code;

    @EntityColumn(length = 32, comment = "名称")
    private String name;

    @EntityColumn(length = 32, comment = "父级编码")
    private String parentCode;

    @EntityColumn(length = 32, comment = "子集", exist = false)
    @TableField(exist = false)
    private List<Area> children;

    @Override
    public EntityClassDomain preCreateTable(EntityClassDomain entityClassDomain, JdbcTemplateHelper jdbcTemplateHelper) {
        return entityClassDomain;
    }

    @Override
    public void postCreateTable(JdbcTemplateHelper jdbcTemplateHelper) {
        Try.run(() -> {
            String tableName = getTableName();
            JdbcTemplate jdbcTemplate = jdbcTemplateHelper.getJdbcTemplate();
            Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Long.class);
            if (count == null || count == 0) {
                ClassPathResource resource = new ClassPathResource("assets/t_open_area.sql");
                File file = resource.getFile();
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                jdbcTemplate.execute(sb.toString());
            }
        }).onFailure(Throwable::printStackTrace);
    }
}
