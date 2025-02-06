package io.github.thebesteric.project.intelligent.core.model.entity.common;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.framework.agile.plugins.database.core.jdbc.JdbcTemplateHelper;
import io.github.thebesteric.framework.agile.plugins.database.core.listener.EntityClassCreateListener;
import io.github.thebesteric.framework.agile.plugins.database.core.listener.EntityClassUpdateListener;
import io.github.thebesteric.project.intelligent.core.base.BaseBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.vavr.control.Try;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 敏感词
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 11:21:33
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.OpenApi.TABLE_NAME_PREFIX + "sensitive")
@EntityClass(comment = "敏感词", schemas = ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public class Sensitive extends BaseBizEntity implements EntityClassCreateListener, EntityClassUpdateListener {
    @Serial
    private static final long serialVersionUID = -1735995981862919148L;

    @EntityColumn(nullable = false, comment = "敏感词")
    private String keyword;

    @Override
    public void postCreateTable(JdbcTemplateHelper jdbcTemplateHelper) {
        this.postUpdateTable(jdbcTemplateHelper);
    }

    @Override
    public void postUpdateTable(JdbcTemplateHelper jdbcTemplateHelper) {
        Try.run(() -> {
            String tableName = getTableName();
            JdbcTemplate jdbcTemplate = jdbcTemplateHelper.getJdbcTemplate();
            Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Long.class);
            if (count == null || count == 0) {
                ClassPathResource resource = new ClassPathResource("asserts/sensitive.json");
                InputStream in = resource.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                List<String> sensitiveKeywords = JsonUtils.MAPPER.readValue(sb.toString(), new TypeReference<>() {
                });
                String insertSql = "INSERT INTO " + tableName + " (`id`, `keyword`, `created_at`) VALUES (?, ?, ?)";
                Snowflake snowflake = IdUtil.getSnowflake(1, 1);
                sensitiveKeywords.forEach(kw -> jdbcTemplate.update(insertSql, snowflake.nextId(), kw, new Date()));
            }
        }).onFailure(Throwable::printStackTrace);
    }
}
