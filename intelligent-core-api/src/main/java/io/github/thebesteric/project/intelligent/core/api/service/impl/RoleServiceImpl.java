package io.github.thebesteric.project.intelligent.core.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.api.generator.SimpleDictShortCodeGenerator;
import io.github.thebesteric.project.intelligent.core.api.mapper.RoleMapper;
import io.github.thebesteric.project.intelligent.core.api.model.constant.RoleType;
import io.github.thebesteric.project.intelligent.core.api.model.entity.Role;
import io.github.thebesteric.project.intelligent.core.api.service.RoleService;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * RoleServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:19:25
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final SimpleDictShortCodeGenerator codeGenerator;

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    @Transactional
    @Override
    public void init() {
        List<Role> existsRoles = this.list(RoleType.SYSTEM);
        Role role = Role.of(ApplicationConstants.SYSTEM_TENANT_ID, "超级管理员", RoleType.SYSTEM, codeGenerator, existsRoles);
        this.saveIfNotNull(role);
    }

    /**
     * 角色列表
     *
     * @param type 角色类型
     *
     * @return List<Role>
     *
     * @author wangweijun
     * @since 2024/12/9 14:00
     */
    @Override
    public List<Role> list(RoleType type) {
        return this.lambdaQuery().eq(Role::getType, type).list();
    }
}
