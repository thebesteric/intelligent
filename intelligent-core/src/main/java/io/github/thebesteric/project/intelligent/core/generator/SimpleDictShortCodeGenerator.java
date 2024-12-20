package io.github.thebesteric.project.intelligent.core.generator;

import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.service.core.PrivilegeService;
import io.github.thebesteric.project.intelligent.core.util.code.AbstractDictShortCodeGenerator;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * SimpleDictShortCodeGenerator
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-05 11:07:49
 */
@Component
@RequiredArgsConstructor
public class SimpleDictShortCodeGenerator extends AbstractDictShortCodeGenerator {

    @Lazy
    @Resource
    private PrivilegeService privilegeService;

    public String generate() {
        return super.generate(6);
    }

    @Override
    public boolean validate(String shortCode, int times) {
        if (times >= 10) {
            throw new BizException("短码生成失败");
        }
        return privilegeService.getByCode(shortCode) == null;
    }
}
