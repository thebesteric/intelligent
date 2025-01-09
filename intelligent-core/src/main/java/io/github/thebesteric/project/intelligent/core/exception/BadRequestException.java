package io.github.thebesteric.project.intelligent.core.exception;

import java.io.Serial;

/**
 * 参数校验异常
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 11:48:14
 */
public class BadRequestException extends BizException {
    @Serial
    private static final long serialVersionUID = -8270405734924285317L;

    public BadRequestException() {
        this(BizCode.BAD_REQUEST.getMessage());
    }

    public BadRequestException(String message) {
        super(BizCode.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Object... args) {
        super(BizCode.BAD_REQUEST, message, args);
    }
}
