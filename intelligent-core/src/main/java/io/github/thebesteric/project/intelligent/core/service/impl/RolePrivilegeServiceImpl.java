package io.github.thebesteric.project.intelligent.core.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.RoleType;
import io.github.thebesteric.project.intelligent.core.mapper.RolePrivilegeMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Privilege;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Role;
import io.github.thebesteric.project.intelligent.core.model.entity.core.RolePrivilege;
import io.github.thebesteric.project.intelligent.core.service.PrivilegeService;
import io.github.thebesteric.project.intelligent.core.service.RolePrivilegeService;
import io.github.thebesteric.project.intelligent.core.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * RolePrivilegeServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:26:25
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
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
            List<RolePrivilege> existsRolePrivileges = this.listByRoleIds(role.getId());
            for (Privilege privilege : privileges) {
                RolePrivilege rolePrivilege = RolePrivilege.of(ApplicationConstants.SYSTEM_TENANT_ID, role, privilege, existsRolePrivileges);
                this.saveIfNotNull(rolePrivilege);
            }
        }
    }

    /**
     * 根据角色获取所有角色权限
     *
     * @param roleIds 角色 IDs
     *
     * @return List<RolePrivilege>
     *
     * @author wangweijun
     * @since 2024/12/16 14:33
     */
    @Override
    public List<RolePrivilege> listByRoleIds(Long... roleIds) {
        Set<RolePrivilege> result = new LinkedHashSet<>();
        for (Long roleId : roleIds) {
            List<RolePrivilege> rolePrivileges = this.lambdaQuery().eq(RolePrivilege::getRoleId, roleId).list();
            result.addAll(rolePrivileges);
        }
        return result.stream().toList();
    }
}
