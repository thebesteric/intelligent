package io.github.thebesteric.project.intelligent.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientDTO;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientVO;
import io.github.thebesteric.project.intelligent.gateway.model.entity.Oauth2RegisteredClient;

public interface Oauth2RegisteredClientService extends IService<Oauth2RegisteredClient> {
    Oauth2RegisteredClientVO register(Oauth2RegisteredClientDTO dto);
}
