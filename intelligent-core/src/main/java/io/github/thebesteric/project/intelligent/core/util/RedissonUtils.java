package io.github.thebesteric.project.intelligent.core.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import io.github.thebesteric.framework.agile.commons.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * RedissonUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-21 11:48:37
 */
public class RedissonUtils extends AbstractUtils {

    private static final String KEY_PREFIX = "key:intelligent:";

    public static String getKey(String moduleName, String suffix) {
        if (StringUtils.isBlank(moduleName)) {
            return getKey(suffix);
        }
        return MessageUtils.format(KEY_PREFIX + "{}:", moduleName) + suffix;
    }

    public static String getKey(String suffix) {
        return KEY_PREFIX + suffix;
    }

    public static String getCurrentMethodNameKey(String moduleName) {
        return getKey(moduleName, getCurrentMethodName(3));
    }

    public static String getRandomKey(String moduleName) {
        return getKey(moduleName, UUID.randomUUID().toString());
    }

    public static String getCurrentMethodName() {
        return getCurrentMethodName(2);
    }

    public static String getCurrentMethodName(int index) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 假设调用此方法的是上一层堆栈跟踪
        StackTraceElement element = stackTrace[index];
        // 获取方法名
        return element.getClassName() + "#" + element.getMethodName();
    }

}
