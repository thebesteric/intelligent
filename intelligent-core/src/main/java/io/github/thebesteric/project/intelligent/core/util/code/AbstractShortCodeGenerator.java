package io.github.thebesteric.project.intelligent.core.util.code;

/**
 * AbstractShortCodeGenerator
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-09-30 13:31:25
 */
public abstract class AbstractShortCodeGenerator implements ShortCodeGenerator {

    /**
     * 生成指定长度的短码
     *
     * @param length 短码长度
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/9/30 13:30
     */
    @Override
    public String generate(int length) {
        String shortCode;
        int times = 1;

        boolean valid;
        do {
            shortCode = this.doGenerate(length);
            valid = this.validate(shortCode, times);
        } while (!valid);

        return shortCode;
    }

    /**
     * 生成短码
     *
     * @param length 短码长度
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/9/30 13:43
     */
    public abstract String doGenerate(int length);

    /**
     * 校验短码是否符合
     *
     * @param shortCode 短码
     * @param times     已生成次数
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/9/30 13:42
     */
    public abstract boolean validate(String shortCode, int times);
}
