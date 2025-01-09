package io.github.thebesteric.project.intelligent.core.initializer;

import io.github.thebesteric.framework.agile.plugins.sensitive.filter.extension.AgileOtherTypeSensitiveLoader;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Sensitive;
import io.github.thebesteric.project.intelligent.core.service.common.SensitiveService;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * DatabaseSensitiveLoader
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 15:44:49
 */
public class DatabaseAgileSensitiveLoader implements AgileOtherTypeSensitiveLoader {

    @Resource
    private SensitiveService sensitiveService;

    @Override
    public List<String> load() {
        List<Sensitive> list = sensitiveService.lambdaQuery().eq(Sensitive::getState, 1).list();
        return list.stream().map(Sensitive::getKeyword).toList();
    }
}
