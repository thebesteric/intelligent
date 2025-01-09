package io.github.thebesteric.project.intelligent.core.exception;

import java.io.Serial;

/**
 * 数据已存在异常
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 11:40:01
 */
public class DataAlreadyExistsException extends BizException {
    @Serial
    private static final long serialVersionUID = 3040003749030279024L;

    public DataAlreadyExistsException() {
        this(BizCode.DATA_ALREADY_EXISTS.getMessage());
    }

    public DataAlreadyExistsException(String message) {
        super(BizCode.DATA_ALREADY_EXISTS, message);
    }

    public DataAlreadyExistsException(String message, Object... args) {
        super(BizCode.DATA_ALREADY_EXISTS, message, args);
    }
}
