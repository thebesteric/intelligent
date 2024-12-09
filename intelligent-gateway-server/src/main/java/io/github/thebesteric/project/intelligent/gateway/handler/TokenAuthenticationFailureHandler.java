package io.github.thebesteric.project.intelligent.gateway.handler;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

/**
 * TokenAuthenticationFailureHandler
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-19 14:16:40
 */
public class TokenAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        HttpHeaders headers = response.getHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAccessControlAllowOrigin("*");
        R<Object> message = R.error(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JsonUtils.toJson(message).getBytes())));
    }
}
