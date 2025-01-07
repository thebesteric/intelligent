package io.github.thebesteric.project.intelligent.core.exception;

import io.github.thebesteric.framework.agile.commons.util.MessageUtils;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常类
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 17:53:33
 */
@Getter
public class BizException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -816669884341217529L;

    private final BizCode bizCode;

    public BizException() {
        super(BizCode.UNKNOWN_ERROR.getMessage());
        this.bizCode = BizCode.UNKNOWN_ERROR;
    }

    public BizException(String message) {
        super(message);
        this.bizCode = BizCode.UNKNOWN_ERROR;
    }

    public BizException(Throwable cause) {
        this(BizCode.UNKNOWN_ERROR, cause);
    }

    public BizException(String message, Throwable cause) {
        this(BizCode.UNKNOWN_ERROR, message, cause);
    }

    public BizException(String message, Object... args) {
        this(MessageUtils.replacePlaceholders(message, args));
    }

    public BizException(BizCode bizCode) {
        super(bizCode.getMessage());
        this.bizCode = bizCode;
    }

    public BizException(BizCode bizCode, Throwable cause) {
        super(cause);
        this.bizCode = bizCode;
    }

    public BizException(BizCode bizCode, String message) {
        super(message);
        this.bizCode = bizCode;
    }

    public BizException(BizCode bizCode, String message, Throwable cause) {
        super(message, cause);
        this.bizCode = bizCode;
    }

    public BizException(BizCode bizCode, String message, Object... args) {
        this(bizCode, MessageUtils.replacePlaceholders(message, args));
    }

    @Getter
    public enum BizCode {
        // 成功
        SUCCESS("00", "000", "成功"),

        // 数据校验相关
        BAD_REQUEST("40", "000", "错误的请求"),
        DATA_ALREADY_EXISTS("40", "001", "数据已存在，请核实"),
        DATA_NOT_FOUND("40", "002", "数据不存在，请核实"),
        INVALID_PARAM_ERROR("40", "003", "参数校验异常"),
        INVALID_DATA_ERROR("40", "004", "数据校验异常"),
        ILLEGAL_REQUEST("40", "999", "非法的请求"),

        // 未知异常
        UNKNOWN_ERROR("99", "999", "未知异常，请联系系统管理员");

        public final String group;
        public final String code;
        public final String errorCode;

        private String message;

        BizCode(String group, String code, String message) {
            this.group = group;
            this.code = code;
            this.message = message;
            this.errorCode = group + code;
        }

        public BizCode setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
