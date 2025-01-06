package io.github.thebesteric.project.intelligent.core.exception;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.distributed.locks.exeption.DistributedLocksException;
import io.github.thebesteric.framework.agile.plugins.idempotent.exception.IdempotentException;
import io.github.thebesteric.framework.agile.plugins.limiter.exception.RateLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * GlobalExceptionHandler
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 16:57:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = WebExchangeBindException.class)
    public R<Map<String, Object>> handleException(WebExchangeBindException ex) {
        R<Map<String, Object>> response = R.error(HttpStatus.BAD_REQUEST.value(), "参数校验错误");
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, Object> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }
        response.setData(errors);
        return response;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<String> handleException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        ObjectError objectError = allErrors.get(allErrors.size() - 1);
        String message = Optional.of(objectError).map(ObjectError::getDefaultMessage).orElse("");
        if (log.isTraceEnabled()) {
            log.trace(ex.getMessage(), ex);
        }
        return R.error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public R<String> handleException(HandlerMethodValidationException ex) {
        Object[] detailMessageArguments = ex.getDetailMessageArguments();
        List<String> allErrors = detailMessageArguments == null ? List.of() : Stream.of(detailMessageArguments).map(Object::toString).toList();
        String message = allErrors.isEmpty() ? "Validation failed" : allErrors.get(0);
        if (log.isTraceEnabled()) {
            log.trace(ex.getMessage(), ex);
        }
        return R.error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public R<String> handleException(MissingRequestHeaderException ex) {
        String message = ex.getMessage();
        return R.error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R<String> handleException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String parameterType = ex.getParameterType();
        if (log.isTraceEnabled()) {
            log.trace(ex.getMessage(), ex);
        }
        String message = String.format("缺少类型为 %s 的参数: %s", parameterType, parameterName);
        return R.error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(value = BizException.class)
    public R<String> handleException(BizException e) {
        String errorCode = e.getBizCode().getErrorCode();
        if (BizException.BizCode.SUCCESS.getErrorCode().equals(errorCode)) {
            return R.success(HttpStatus.OK, e.getMessage());
        }
        log.error(e.getMessage(), e);
        return R.error(Integer.parseInt(errorCode), e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public R<String> handleException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public R<String> handleException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public R<String> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<String> handleMaxUploadSizeExceededException(Exception e) {
        String msg = "所上传文件大小超过最大限制，上传失败";
        log.error(msg, e);
        return R.error(HttpStatus.PAYLOAD_TOO_LARGE.value(), msg);
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public R<String> handleException(BadSqlGrammarException e) {
        log.error(e.getMessage(), e);
        String message = e.getCause().getMessage();
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    @ExceptionHandler(value = SQLException.class)
    public R<String> handleException(SQLException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public R<String> handleException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        String message = e.getCause().getMessage();
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    @ExceptionHandler(value = RateLimitException.class)
    public R<String> handleException(RateLimitException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = IdempotentException.class)
    public R<String> handleException(IdempotentException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = DistributedLocksException.class)
    public R<String> handleException(DistributedLocksException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public R<String> handleException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知错误，请联系管理员");
    }

}
