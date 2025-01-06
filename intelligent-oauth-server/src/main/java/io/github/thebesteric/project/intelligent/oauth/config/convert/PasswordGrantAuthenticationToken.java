package io.github.thebesteric.project.intelligent.oauth.config.convert;

import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

/**
 * PasswordGrantAuthenticationToken
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-08-19 10:26:23
 */
public class PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    public PasswordGrantAuthenticationToken(Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(new AuthorizationGrantType(GrantType.GRANT_TYPE_PASSWORD.getType()), clientPrincipal, additionalParameters);
    }

    @Override
    public Object getCredentials() {
        return this.getAdditionalParameters().get(OAuth2ParameterNames.PASSWORD);
    }
}
