package io.github.thebesteric.project.intelligent.core.api.mapper;

import io.github.thebesteric.project.intelligent.core.api.model.entity.User;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:20:25
 */
@Mapper
public interface UserMapper extends BaseRepositoryMapper<User> {
}
