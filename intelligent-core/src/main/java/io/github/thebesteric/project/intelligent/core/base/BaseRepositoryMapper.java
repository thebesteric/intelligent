package io.github.thebesteric.project.intelligent.core.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface BaseRepositoryMapper<T extends BaseEntity> extends BaseMapper<T> {
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
}
