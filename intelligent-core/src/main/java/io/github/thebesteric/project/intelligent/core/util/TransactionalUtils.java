package io.github.thebesteric.project.intelligent.core.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import org.springframework.aop.framework.AopContext;

/**
 * TransactionalUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-07-23 12:37:43
 */
public class TransactionalUtils extends AbstractUtils {

    @SuppressWarnings("unchecked")
    public static <T> T currentProxy(Class<T> clazz) {
        return (T) AopContext.currentProxy();
    }

}
