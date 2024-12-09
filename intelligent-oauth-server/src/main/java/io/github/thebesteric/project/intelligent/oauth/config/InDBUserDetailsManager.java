package io.github.thebesteric.project.intelligent.oauth.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.oauth.mapper.ShadowUserMapper;
import io.github.thebesteric.project.intelligent.oauth.model.domain.ShadowUserDomain;
import io.github.thebesteric.project.intelligent.oauth.model.entity.ShadowUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * InDBUserDetailsManager
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 13:33:29
 */
@RequiredArgsConstructor
public class InDBUserDetailsManager implements UserDetailsService {

    private final ShadowUserMapper shadowUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<ShadowUser> queryWrapper = new LambdaQueryWrapper<ShadowUser>()
                .eq(ShadowUser::getUsername, username);
        // User 实现了 UserDetails
        ShadowUser shadowUser = shadowUserMapper.selectOne(queryWrapper);
        if (shadowUser == null) {
            throw new UsernameNotFoundException("用户或密码错误");
        }
        if (shadowUser.getState() == 0) {
            throw new BizException(BizException.BizCode.DATA_VALID_ERROR, "用户已禁用");
        }

        ShadowUserDomain shadowUserDomain = ShadowUserDomain.of(shadowUser);

        // 查找权限
        List<String> privileges = new ArrayList<>();
        shadowUserDomain.setAuthorities(AuthorityUtils.createAuthorityList(privileges));

        List<SimpleGrantedAuthority> grantedAuthorities = privileges.stream().map(SimpleGrantedAuthority::new).toList();
        return new org.springframework.security.core.userdetails.User(username, shadowUser.getPassword(), grantedAuthorities);
    }
}
