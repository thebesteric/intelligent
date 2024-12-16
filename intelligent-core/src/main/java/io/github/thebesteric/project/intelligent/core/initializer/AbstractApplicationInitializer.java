package io.github.thebesteric.project.intelligent.core.initializer;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

/**
 * AbstractApplicationInitializer
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 14:00:39
 */
public abstract class AbstractApplicationInitializer implements ApplicationInitializer {
    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        initialize();
    }
}
