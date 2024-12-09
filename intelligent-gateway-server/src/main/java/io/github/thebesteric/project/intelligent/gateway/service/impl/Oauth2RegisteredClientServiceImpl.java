package io.github.thebesteric.project.intelligent.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.util.IdentityUtils;
import io.github.thebesteric.project.intelligent.gateway.mapper.Oauth2RegisteredClientMapper;
import io.github.thebesteric.project.intelligent.gateway.model.conver.Oauth2RegisteredClientConvert;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientDTO;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientVO;
import io.github.thebesteric.project.intelligent.gateway.model.entity.Oauth2RegisteredClient;
import io.github.thebesteric.project.intelligent.gateway.service.Oauth2RegisteredClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Oauth2RegisteredClientServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 16:14:52
 */
@Service
@RequiredArgsConstructor
@Transactional
public class Oauth2RegisteredClientServiceImpl extends ServiceImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClient> implements Oauth2RegisteredClientService {

    public final Oauth2RegisteredClientConvert clientConvert;

    @Override
    public Oauth2RegisteredClientVO register(Oauth2RegisteredClientDTO dto) {
        // 查询是否存在相同的客户端ID
        LambdaQueryWrapper<Oauth2RegisteredClient> queryWrapper = new LambdaQueryWrapper<Oauth2RegisteredClient>()
                .eq(Oauth2RegisteredClient::getClientName, dto.getClientName());
        Oauth2RegisteredClient client = this.getOne(queryWrapper);
        if (client != null) {
            throw new BizException(BizException.BizCode.DATA_ALREADY_EXISTS, "客户端已注册");
        }

        // DTO 转换为 DO
        Oauth2RegisteredClient oauth2RegisteredClient = clientConvert.parseDTO(dto);

        // 添加密码授权模式
        if (dto.isIncludeGrantTypePassword()) {
            oauth2RegisteredClient.appendPasswordAuthorizationGrantType();
        }

        // 添加 OpenId 权限范围
        if (dto.isIncludeOpenIdScope()) {
            oauth2RegisteredClient.appendOpenIdScope();
        }

        // 生成唯一身份（appKey, appSecret）
        IdentityUtils.Identity identity = IdentityUtils.generateIdentity();

        // 设置客户端账号与密码
        oauth2RegisteredClient.setIdentity(identity);

        // 保存客户端信息
        this.save(oauth2RegisteredClient);

        // DO 转换为 VO
        Oauth2RegisteredClientVO oauth2RegisteredClientVO = clientConvert.toVO(oauth2RegisteredClient);
        oauth2RegisteredClientVO.setClientSecret(identity.getAppSecret());
        oauth2RegisteredClientVO.setIncludeGrantTypePassword(oauth2RegisteredClient.includeGrantTypePassword());
        oauth2RegisteredClientVO.setIncludeOpenIdScope(oauth2RegisteredClient.includeOpenIdScope());

        return oauth2RegisteredClientVO;
    }
}
