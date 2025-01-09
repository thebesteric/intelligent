package io.github.thebesteric.project.intelligent.core.exception;

import java.io.Serial;

/**
 * 参数校验异常
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 11:48:14
 */
public class InvalidParamsException extends BizException {
    @Serial
    private static final long serialVersionUID = -8270405734924285317L;

    public InvalidParamsException() {
        this(BizCode.INVALID_PARAM_ERROR.getMessage());
    }

    public InvalidParamsException(String message) {
        super(BizCode.INVALID_PARAM_ERROR, message);
    }

    public InvalidParamsException(String message, Object... args) {
        super(BizCode.INVALID_PARAM_ERROR, message, args);
    }
}
