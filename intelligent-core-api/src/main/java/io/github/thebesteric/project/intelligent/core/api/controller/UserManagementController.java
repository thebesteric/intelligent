package io.github.thebesteric.project.intelligent.core.api.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.core.model.domain.core.request.UserCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.core.request.UserUpdateRequest;
import io.github.thebesteric.project.intelligent.core.service.core.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 16:33:25
 */
@RestController
@RequestMapping("/user/management")
@RequiredArgsConstructor
@Tag(name = "用户-用户管理")
public class UserManagementController {

    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "新增用户")
    public R<Void> create(@RequestBody @Validated UserCreateRequest createRequest) {
        userService.create(createRequest);
        return R.success();
    }

    @PostMapping("/update")
    @Operation(summary = "更新用户")
    public R<Void> create(@RequestBody @Validated UserUpdateRequest updateRequest) {
        userService.update(updateRequest);
        return R.success();
    }

}
