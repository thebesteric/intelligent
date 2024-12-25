package io.github.thebesteric.project.intelligent.core.exception;

import java.io.Serial;

/**
 * 数据校验异常
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 11:48:14
 */
public class InvalidDataException extends BizException {
    @Serial
    private static final long serialVersionUID = -8270405734924285317L;

    public InvalidDataException() {
        this("数据校验异常");
    }

    public InvalidDataException(String message) {
        super(BizCode.DATA_VALID_ERROR, message);
    }
}
