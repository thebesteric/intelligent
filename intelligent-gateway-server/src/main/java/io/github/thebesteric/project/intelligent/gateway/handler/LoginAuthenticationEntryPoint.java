package io.github.thebesteric.project.intelligent.gateway.handler;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class LoginAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        HttpHeaders headers = response.getHeaders();
        headers.set("Content-Type", "application/json");
        R<Object> message = R.error(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JsonUtils.toJson(message).getBytes())));
    }
}
