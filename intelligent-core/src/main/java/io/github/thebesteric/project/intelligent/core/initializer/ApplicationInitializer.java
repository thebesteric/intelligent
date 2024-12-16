package io.github.thebesteric.project.intelligent.core.initializer;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public interface ApplicationInitializer extends ApplicationListener<ContextRefreshedEvent> {
    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/10 14:01
     */
    void initialize();
}
