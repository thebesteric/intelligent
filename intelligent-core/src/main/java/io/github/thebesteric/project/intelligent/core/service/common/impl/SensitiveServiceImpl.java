package io.github.thebesteric.project.intelligent.core.service.common.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.plugins.sensitive.filter.AgileSensitiveFilter;
import io.github.thebesteric.framework.agile.plugins.sensitive.filter.domain.SensitiveFilterResult;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.exception.InvalidParamsException;
import io.github.thebesteric.project.intelligent.core.mapper.common.SensitiveMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Sensitive;
import io.github.thebesteric.project.intelligent.core.service.common.SensitiveService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SensitiveServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 13:49:12
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper, Sensitive> implements SensitiveService {

    @Lazy
    @Resource
    private AgileSensitiveFilter agileSensitiveFilter;

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
    @Override
    public SensitiveFilterResult filter(String text) {
        return agileSensitiveFilter.filter(text);
    }

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
    @Override
    public String getResult(String text) {
        return agileSensitiveFilter.getResult(text);
    }

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
    @Override
    public String getResultOrElse(String text, String defaultValue) {
        return agileSensitiveFilter.getResultOrElse(text, defaultValue);
    }

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
    @Override
    public String getResultOrElseThrow(String text) {
        return agileSensitiveFilter.getResultOrElseThrow(text);
    }

    /**
     * 启用敏感词
     *
     * @param ids 敏感词 IDs
     *
     * @author wangweijun
     * @since 2025/1/9 17:05
     */
    @Override
    public void enable(List<Long> ids) {
        Processor.prepare()
                .validate(CollectionUtils.isEmpty(ids), new InvalidParamsException("ids cannot be empty"))
                .next(() -> {
                    List<Sensitive> sensitives = listByIds(ids);
                    sensitives.forEach(sensitive -> {
                        if (sensitive.getState() == 0) {
                            sensitive.setState(1);
                            updateById(sensitive);
                        }
                    });
                })
                .complete(() -> agileSensitiveFilter.reload());
    }

    /**
     * 禁用敏感词
     *
     * @param ids 敏感词 IDs
     *
     * @author wangweijun
     * @since 2025/1/9 17:05
     */
    @Transactional
    @Override
    public void disable(List<Long> ids) {
        Processor.prepare()
                .validate(CollectionUtils.isEmpty(ids), new InvalidParamsException("ids cannot be empty"))
                .next(() -> {
                    List<Sensitive> sensitives = listByIds(ids);
                    sensitives.forEach(sensitive -> {
                        if (sensitive.getState() == 1) {
                            sensitive.setState(0);
                            updateById(sensitive);
                        }
                    });
                })
                .complete(() -> agileSensitiveFilter.reload());
    }

    /**
     * 添加敏感词
     *
     * @param keywords 敏感词
     *
     * @author wangweijun
     * @since 2025/1/9 17:05
     */
    @Override
    public void add(List<String> keywords) {

    }
}
