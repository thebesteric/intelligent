package io.github.thebesteric.project.intelligent.oauth.config;

import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.constant.security.DeviceType;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * SecurityContextHolder
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-05-22 14:23:38
 */
public class SecurityContextHolder {

    private static final ThreadLocal<UserDetails> USER_DETAILS_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<AuthType> AUTH_TYPE_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<AuthSource> AUTH_SOURCE_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<DeviceType> AUTH_DEVICE_TYPE = new ThreadLocal<>();


    private SecurityContextHolder() {
        super();
    }

    public static void setUserDetails(UserDetails userDetails) {
        USER_DETAILS_HOLDER.set(userDetails);
    }

    public static void setAuthType(AuthType authType) {
        AUTH_TYPE_HOLDER.set(authType);
    }

    public static void setAuthSource(AuthSource authSource) {
        AUTH_SOURCE_HOLDER.set(authSource);
    }

    public static void setDeviceType(DeviceType deviceType) {
        AUTH_DEVICE_TYPE.set(deviceType);
    }

    public static UserDetails getUserDetails() {
        return USER_DETAILS_HOLDER.get();
    }

    public static AuthType getAuthType() {
        return AUTH_TYPE_HOLDER.get();
    }

    public static AuthSource getAuthSource() {
        return AUTH_SOURCE_HOLDER.get();
    }

    public static DeviceType getDeviceType() {
        return AUTH_DEVICE_TYPE.get();
    }

    public static void remove() {
        USER_DETAILS_HOLDER.remove();
        AUTH_TYPE_HOLDER.remove();
        AUTH_SOURCE_HOLDER.remove();
        AUTH_DEVICE_TYPE.remove();
    }

}
