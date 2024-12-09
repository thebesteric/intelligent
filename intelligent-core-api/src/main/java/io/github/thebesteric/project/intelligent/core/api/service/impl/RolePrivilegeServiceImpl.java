package io.github.thebesteric.project.intelligent.core.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.api.mapper.RolePrivilegeMapper;
import io.github.thebesteric.project.intelligent.core.api.model.constant.RoleType;
import io.github.thebesteric.project.intelligent.core.api.model.entity.Privilege;
import io.github.thebesteric.project.intelligent.core.api.model.entity.Role;
import io.github.thebesteric.project.intelligent.core.api.model.entity.RolePrivilege;
import io.github.thebesteric.project.intelligent.core.api.service.PrivilegeService;
import io.github.thebesteric.project.intelligent.core.api.service.RolePrivilegeService;
import io.github.thebesteric.project.intelligent.core.api.service.RoleService;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * RolePrivilegeServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:26:25
 */
@Service
@RequiredArgsConstructor
public class RolePrivilegeServiceImpl extends ServiceImpl<RolePrivilegeMapper, RolePrivilege> implements RolePrivilegeService {

    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    @Transactional
    @Override
    public void init() {
        List<Role> roles = roleService.list(RoleType.SYSTEM);
        List<Privilege> privileges = privilegeService.list();
        for (Role role : roles) {
            List<RolePrivilege> existsRolePrivileges = this.list(role);
            for (Privilege privilege : privileges) {
                RolePrivilege rolePrivilege = RolePrivilege.of(ApplicationConstants.SYSTEM_TENANT_ID, role, privilege, existsRolePrivileges);
                this.saveIfNotNull(rolePrivilege);
            }
        }
    }

    /**
     * 根据角色获取所有角色权限关联列表
     *
     * @param role 角色
     *
     * @return List<RolePrivilege>
     *
     * @author wangweijun
     * @since 2024/12/9 14:18
     */
    @Override
    public List<RolePrivilege> list(Role role) {
        return this.lambdaQuery().eq(RolePrivilege::getRoleId, role.getId()).list();
    }
}
