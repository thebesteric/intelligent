package io.github.thebesteric.project.intelligent.core.api.initializer;

import io.github.thebesteric.project.intelligent.core.initializer.AbstractApplicationInitializer;
import io.github.thebesteric.project.intelligent.core.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * PrivilegeInitializer
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 13:57:04
 */
@Component
@RequiredArgsConstructor
public class PrivilegeInitializer extends AbstractApplicationInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final RolePrivilegeService rolePrivilegeService;
    private final UserRoleService userRoleService;

    /**
     * 系统初始化
     *
     * @author wangweijun
     * @since 2024/12/9 14:09
     */
    @Override
    public void initialize() {
        userService.init();
        roleService.init();
        privilegeService.init();
        rolePrivilegeService.init();
        userRoleService.init();
    }

}
