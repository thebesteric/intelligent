package io.github.thebesteric.project.intelligent.oauth.config;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.oauth.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * 会话并发处理策略
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-08-09 18:26:01
 */
public class JsonSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        ServletUtils.renderString(response, R.error(HttpStatus.UNAUTHORIZED, "账号已从其他设备登录"));
    }
}
