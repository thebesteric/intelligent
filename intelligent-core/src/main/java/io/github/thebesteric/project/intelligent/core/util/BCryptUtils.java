package io.github.thebesteric.project.intelligent.core.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SecretUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 22:28:39
 */
public class BCryptUtils extends AbstractUtils {

    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
