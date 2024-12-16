package io.github.thebesteric.project.intelligent.core.base;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

public interface BaseRepositoryMapper<T extends BaseEntity> extends BaseMapper<T> {

    /**
     * 根据列进行物理删除
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param value      列值
     *
     * @return int
     *
     * @author wangweijun
     * @since 2024/12/12 14:43
     */
    @Delete("delete from #{tableName} where #{columnName} = #{value}")
    int deleteByColumn(String tableName, String columnName, Object value);

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
        return this.deleteByColumn(tableName, "id", id);
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
        EntityClass entityClassAnno = clazz.getAnnotation(EntityClass.class);
        if (entityClassAnno != null && !entityClassAnno.value().isEmpty()) {
            tableName = entityClassAnno.value();
        }
        if (tableName == null || tableName.isEmpty()) {
            TableName tableNameAnno = clazz.getAnnotation(TableName.class);
            if (tableNameAnno != null && !tableNameAnno.value().isEmpty()) {
                tableName = tableNameAnno.value();
            }
        }
        return tableName;
    }
}
