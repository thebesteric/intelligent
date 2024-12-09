package io.github.thebesteric.project.intelligent.gateway.model.conver;

import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientDTO;
import io.github.thebesteric.project.intelligent.gateway.model.domain.Oauth2RegisteredClientVO;
import io.github.thebesteric.project.intelligent.gateway.model.entity.Oauth2RegisteredClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Oauth2RegisteredClientConvert
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 18:29:36
 */
@Mapper(componentModel = "spring")
public abstract class Oauth2RegisteredClientConvert {
    public static final Oauth2RegisteredClientConvert INSTANCE = Mappers.getMapper(Oauth2RegisteredClientConvert.class);

    @Mapping(target = "redirectUris", expression = "java(java.util.Arrays.asList(client.getRedirectUris().split(\",\")))")
    @Mapping(target = "postLogoutRedirectUris", expression = "java(java.util.Arrays.asList(client.getPostLogoutRedirectUris().split(\",\")))")
    @Mapping(target = "scopes", expression = "java(java.util.Arrays.asList(client.getScopes().split(\",\")))")
    public abstract Oauth2RegisteredClientVO toVO(Oauth2RegisteredClient client);

    @Mapping(target = "redirectUris", expression = "java(String.join(\",\", dto.getRedirectUris()))")
    @Mapping(target = "postLogoutRedirectUris", expression = "java(String.join(\",\", dto.getPostLogoutRedirectUris()))")
    public abstract Oauth2RegisteredClient parseDTO(Oauth2RegisteredClientDTO dto);
}
