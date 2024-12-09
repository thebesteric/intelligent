package io.github.thebesteric.project.intelligent.core.security;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class SecurityAuthenticationException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = -8844676067604766023L;

    public SecurityAuthenticationException(String msg) {
        super(msg);
    }

    public SecurityAuthenticationException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}