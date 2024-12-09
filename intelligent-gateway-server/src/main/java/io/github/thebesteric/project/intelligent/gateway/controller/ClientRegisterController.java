package io.github.thebesteric.project.intelligent.gateway.controller;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.Idempotent;
import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientDTO;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientVO;
import io.github.thebesteric.project.intelligent.gateway.service.Oauth2RegisteredClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * ClientRegisterController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-19 22:13:13
 */
@RestController
@RequestMapping("/client")
@RefreshScope
@RequiredArgsConstructor
@AgileLogger
public class ClientRegisterController {

    private final Oauth2RegisteredClientService oauth2RegisteredClientService;

    /**
     * 客户端注册<br>
     * 注意：返回的原始密码，必须妥善保管，系统无法查看
     */
    @Idempotent
    @PostMapping("/register")
    public Mono<R<Oauth2RegisteredClientVO>> register(@Validated @RequestBody Oauth2RegisteredClientDTO dto) {
        Oauth2RegisteredClientVO data = oauth2RegisteredClientService.register(dto);
        return Mono.just(R.success("注册成功", data));
    }

}
