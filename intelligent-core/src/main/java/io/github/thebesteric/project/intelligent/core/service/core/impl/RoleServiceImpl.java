package io.github.thebesteric.project.intelligent.core.service.core.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.core.RoleCode;
import io.github.thebesteric.project.intelligent.core.constant.core.RoleType;
import io.github.thebesteric.project.intelligent.core.generator.SimpleDictShortCodeGenerator;
import io.github.thebesteric.project.intelligent.core.mapper.core.RoleMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Role;
import io.github.thebesteric.project.intelligent.core.service.core.RoleService;
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
@DS(ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
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
        Role role = Role.of(RoleCode.SUPER_ADMIN, existsRoles);
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
