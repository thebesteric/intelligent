package io.github.thebesteric.project.intelligent.core.service.common;

import io.github.thebesteric.framework.agile.plugins.sensitive.filter.domain.SensitiveFilterResult;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Sensitive;

import java.util.List;

/**
 * SensitiveService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 13:48:43
 */
public interface SensitiveService extends IBaseService<Sensitive> {

    /**
     * 获取结果
     *
     * @param text 待检测文本
     *
     * @return 完整结果
     *
     * @author wangweijun
     * @since 2025/1/9 16:42
     */
    SensitiveFilterResult filter(String text);

    /**
     * 获取结果
     *
     * @param text 待检测文本
     *
     * @return 返回检测后的文本
     *
     * @author wangweijun
     * @since 2025/1/9 16:39
     */
    String getResult(String text);


    /**
     * 获取结果
     *
     * @param text         待检测文本
     * @param defaultValue 默认值
     *
     * @return 出现异常返回默认值
     *
     * @author wangweijun
     * @since 2025/1/9 16:39
     */
    String getResultOrElse(String text, String defaultValue);

    /**
     * 获取结果
     *
     * @param text 待检测文本
     *
     * @return 检测失败抛出异常
     *
     * @author wangweijun
     * @since 2025/1/9 16:39
     */
    String getResultOrElseThrow(String text);

    /**
     * 启用敏感词
     *
     * @param ids 敏感词 IDs
     *
     * @author wangweijun
     * @since 2025/1/9 17:05
     */
    void enable(List<Long> ids);

    /**
     * 禁用敏感词
     *
     * @param ids 敏感词 IDs
     *
     * @author wangweijun
     * @since 2025/1/9 17:05
     */
    void disable(List<Long> ids);

    /**
     * 添加敏感词
     *
     * @param keywords 敏感词
     *
     * @author wangweijun
     * @since 2025/1/9 17:05
     */
    void add(List<String> keywords);
}
