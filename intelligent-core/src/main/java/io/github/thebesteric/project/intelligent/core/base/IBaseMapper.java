package io.github.thebesteric.project.intelligent.core.base;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface IBaseMapper<T extends BaseEntity> extends BaseMapper<T> {

    /**
     * 非空时保存
     *
     * @param entity 实体类
     *
     * @return int
     *
     * @author wangweijun
     * @since 2024/12/6 15:28
     */
    default int saveIfNotNull(T entity) {
        if (entity == null || entity.getId() != null) {
            return 0;
        }
        return this.insert(entity);
    }

    /**
     * 非空时保存
     *
     * @param entityList 实体类列表
     *
     * @author wangweijun
     * @since 2024/12/6 15:43
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    default void saveBatchIfNotNull(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        for (T entity : entityList) {
            saveIfNotNull(entity);
        }
    }

    /**
     * 根据列进行物理删除
     *
     * @param tableName 表名
     * @param params    参数
     *
     * @return int
     *
     * @author wangweijun
     * @since 2024/12/12 14:43
     */
    @Delete("""
            <script>
                DELETE FROM ${tableName}
                <where>
                    <choose>
                        <when test="params != null and params.size > 0">
                            <foreach collection="params" index="key" item="value" separator="and">
                                ${key} = #{value}
                            </foreach>
                        </when>
                        <otherwise>
                            AND 1 = 2
                        </otherwise>
                    </choose>
                </where>
            </script>
            """)
    int physicalDeleteByColumns(String tableName, Map<String, Object> params);

    /**
     * 物理删除
     *
     * @param clazz  实体类
     * @param params 参数
     *
     * @return int
     *
     * @author wangweijun
     * @since 2024/12/12 14:42
     */
    default int physicalDeleteByColumns(Class<T> clazz, Map<String, Object> params) {
        String tableName = getTableName(clazz);
        return this.physicalDeleteByColumns(tableName, params);
    }

    /**
     * 物理删除
     *
     * @param entity 实体类
     *
     * @return int
     *
     * @author wangweijun
     * @since 2024/12/12 14:42
     */
    @SuppressWarnings("unchecked")
    default int physicalDeleteById(T entity) {
        Class<T> clazz = (Class<T>) entity.getClass();
        Long id = entity.getId();
        return this.physicalDeleteById(clazz, id);
    }

    /**
     * 物理删除
     *
     * @param clazz 实体类
     * @param id    主键
     *
     * @return int
     *
     * @author wangweijun
     * @since 2024/12/12 14:42
     */
    default int physicalDeleteById(Class<T> clazz, Serializable id) {
        String tableName = getTableName(clazz);
        return this.physicalDeleteByColumns(tableName, Map.of("id", id));
    }

    /**
     * 获取表名
     *
     * @param entity 实体类
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/12 14:42
     */
    @SuppressWarnings("unchecked")
    default String getTableName(T entity) {
        return getTableName((Class<T>) entity.getClass());
    }

    /**
     * 获取表名
     *
     * @param clazz 实体类
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/12 14:41
     */
    default String getTableName(Class<T> clazz) {
        String simpleClassName = clazz.getSimpleName();
        String tableName = CharSequenceUtil.toUnderlineCase(simpleClassName);
        TableName tableNameAnno = clazz.getAnnotation(TableName.class);
        if (tableNameAnno != null && StringUtils.isNotBlank(tableNameAnno.value())) {
            tableName = tableNameAnno.value();
        }
        EntityClass entityClassAnno = clazz.getAnnotation(EntityClass.class);
        if (entityClassAnno != null && !entityClassAnno.value().isEmpty()) {
            tableName = entityClassAnno.value();
        }
        return tableName;
    }
}
