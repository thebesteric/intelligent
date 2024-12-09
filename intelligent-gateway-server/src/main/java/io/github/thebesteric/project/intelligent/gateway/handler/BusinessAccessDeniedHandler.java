package io.github.thebesteric.project.intelligent.gateway.handler;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class BusinessAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        HttpHeaders headers = response.getHeaders();
        headers.set("Content-Type", "application/json");
        R<Object> message = R.error(HttpStatus.FORBIDDEN, "权限不足");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JsonUtils.toJson(message).getBytes())));
    }
}
