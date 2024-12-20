package io.github.thebesteric.project.intelligent.core.exception;

import java.io.Serial;

/**
 * 数据未找到异常
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 11:20:20
 */
public class DataNotFoundException extends BizException {
    @Serial
    private static final long serialVersionUID = 6225586610980929742L;

    public DataNotFoundException() {
        this("数据不存在");
    }

    public DataNotFoundException(String message) {
        super(BizCode.DATA_NOT_FOUND, message);
    }
}
