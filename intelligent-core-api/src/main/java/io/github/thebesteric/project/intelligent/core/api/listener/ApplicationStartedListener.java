package io.github.thebesteric.project.intelligent.core.api.listener;

import io.github.thebesteric.project.intelligent.core.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * ApplicationListener
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-06 09:46:02
 */
@Component
@RequiredArgsConstructor
public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final RolePrivilegeService rolePrivilegeService;
    private final UserRoleService userRoleService;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        this.systemInitialize();
    }

    /**
     * 系统初始化
     *
     * @author wangweijun
     * @since 2024/12/9 14:09
     */
    private void systemInitialize() {
        userService.init();
        roleService.init();
        privilegeService.init();
        rolePrivilegeService.init();
        userRoleService.init();
    }
}
