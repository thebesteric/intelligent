package io.github.thebesteric.project.intelligent.core.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * IdentityUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-21 17:20:54
 */
public class IdentityUtils extends AbstractUtils {


    public static Identity generateIdentity() {
        String appKey = generateAppKey();
        String appSecret = generateAppSecret();
        return new Identity(appKey, appSecret);
    }

    public static String generateAppKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @SneakyThrows
    public static String generateAppSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return DigestUtils.md5Hex(bytes);
    }

    @Data
    @AllArgsConstructor
    public static class Identity {
        private String appKey;
        private String appSecret;
    }

}
