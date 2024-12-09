package io.github.thebesteric.project.intelligent.module.crm.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-29 09:43:40
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    // @SkipAuth
    @GetMapping("/index")
    public R<Void> index() {
        return R.success();
    }

}
